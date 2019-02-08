package com.bootdo.controller.base;

import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.BannerDetailsDao;
import com.bootdo.dao.app.BannerGroupDao;
import com.bootdo.dao.base.AreaDao;
import com.bootdo.dao.system.FilesDao;
import com.bootdo.dao.user.InvalidUserDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.vo.*;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/base")
public class BasisController extends BaseController {
    @Resource
    AreaDao areaDao;
    @Resource
    FilesDao filesDao;
    @Resource
    BannerGroupDao bannerGroupDao;
    @Resource
    BannerDetailsDao bannerDetailsDao;
    @Resource
    InvalidUserDao invalidUserDao;

    @Value("${mobile.imgHttp}")
    private String imgHttp;

    @Value("${mobile.uploadPath}")
    private String uploadPath;

    //    获取地区列表
    @PostMapping("/areaList/{pid}")
    public ResponseDO areaList(@PathVariable String pid) {
        try {
            map.clear();
            map.put("pid", pid);
            map.clear();
            map.put("areaList", areaDao.list(map));
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    /**
     *获取所有的城市
     * @return
     */
    @PostMapping("/allCityList")
    public ResponseDO allCityList() {
        try {
            map.clear();
            map.put("name", "%市");
            List<AreaDO> list = areaDao.list(map);
            map.clear();
            map.put("allCityList", list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }


    @PostMapping("/uploadPic")
    public ResponseDO uploadPic(@RequestParam MultipartFile[] pics, HttpServletRequest request) throws IOException {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        InvalidUserDO invalidUserDO = invalidUserDao.getUserByPhone(loginName);

        List<Map<String, Object>> urls = new ArrayList<>();
        if (pics.length > 0) {
            for (MultipartFile pic : pics) {
                if (!pic.getOriginalFilename().equals("")) {
                    BufferedImage image = ImageIO.read(pic.getInputStream());
                    Map<String, Object> map = new HashMap<>();
                    String filename = pic.getOriginalFilename();
                    String suffer = FilenameUtils.getExtension(filename);
                    String replace = UUID.randomUUID().toString().replace("-", "");
                    String newName = replace + "." + suffer;
                    //3.准备以时间为界限的文件夹
                    String datePathDir = new SimpleDateFormat("yyyy/MM/dd/HH").format(new Date());
                    //4.创建文件夹
                    File temf = new File(uploadPath + "/" + datePathDir);
                    if (!temf.exists()) {
                        temf.mkdirs();
                    }
                    String path = "/" + datePathDir + "/" + newName;
                    String filePath = uploadPath + "/" + path;
                    File file = new File(filePath);
                    pic.transferTo(file);
                    Thumbnails.of(uploadPath + "/" + path)
                            .scale(1f)
                            .outputQuality(0.5f)
                            .toFile(uploadPath + "/" + path);
                    FilesDO filesDO = new FilesDO();
                    filesDO.setWidth(String.valueOf(image.getWidth()));
                    filesDO.setHeight(String.valueOf(image.getHeight()));
                    filesDO.setFileName(newName);
                    filesDO.setFilePath(datePathDir + "/");
                    filesDO.setUploadBy(invalidUserDO.getUserName());
                    filesDO.setUploadDate(new Date());
                    filesDao.save(filesDO);
                    map.put("srcUrl", imgHttp + path);
                    map.put("fileid", filesDO.getId());
                    urls.add(map);
                }
            }
        }
        map.clear();
        map.put("url", urls);
        return new ResponseDO(200, "上传成功", map);
    }

    //获取首页banner轮播图
    @PostMapping("/banner/{deptId}")
    public ResponseDO savePic(@PathVariable String deptId) {

        try {
            map.clear();
            map.put("deptId", deptId);
            map.put("status", "1");
            List<BannerGroupDO> list = bannerGroupDao.list(map);
            List<String> picList = new ArrayList<>();

            list.forEach(bgd -> {
                map.clear();
                map.put("groupId", bgd.getId());
                map.put("sort", "sort");
                map.put("order", "asc");
                List<BannerDetailsDO> list1 = bannerDetailsDao.list(map);
                list1.forEach(bdd -> {
                    FilesDO filesDO = filesDao.get(bdd.getPicId());
                    String wholePath = imgHttp + filesDO.getFilePath() + filesDO.getFileName();
                    picList.add(wholePath);
                });
                bgd.setList(list1);
            });
            map.clear();
            map.put("bannerlist", picList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", map);
    }



}

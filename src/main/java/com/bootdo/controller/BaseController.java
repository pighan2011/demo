package com.bootdo.controller;

import com.alibaba.fastjson.JSON;
import com.bootdo.dao.system.DeptDao;
import com.bootdo.dao.system.FilesDao;
import com.bootdo.easemob.api.impl.EasemobIMUsers;
import com.bootdo.easemob.entity.EasemobResponse;
import com.bootdo.vo.DeptDO;
import com.bootdo.vo.FilesDO;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseController {

    @Resource
    DeptDao deptDao;
    @Resource
    FilesDao filesDao;
    @Value("${mobile.imgHttp}")
    public String imgHttp;


    protected Map<String, Object> map = new HashMap<>();

    protected SimpleDateFormat getSdf() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    protected SimpleDateFormat getSdf(String format) {
        return new SimpleDateFormat(format);
    }

    protected String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }



    //    寻找最顶级的组织机构
    protected DeptDO getDeptDO(Long deptId) {
        boolean flag = true;
        DeptDO deptDO = null;
        do {
            if (deptDO == null) {
                deptDO = deptDao.get(deptId);
            } else if (deptDO.getParentId() != 0) {
                deptDO = deptDao.get(deptDO.getParentId());
            } else {
                flag = false;
            }
        } while (flag);
        return deptDO;
    }

    //    拼接完成图片路径实体
    protected FilesDO getFilesVo(Long id) {
        FilesDO filesDO = filesDao.get(id);
        String wholePath = imgHttp + filesDO.getFilePath() + filesDO.getFileName();
        filesDO.setFilePath(wholePath);
        return filesDO;
    }

    //从消息体中读取数据
    protected String getWholeTextFromRequest(HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str;
        StringBuilder wholeStr = new StringBuilder();
        //一行一行的读取body体里面的内容；
        while ((str = reader.readLine()) != null) {
            wholeStr.append(str);
        }
        return  URLDecoder.decode(wholeStr.toString(), "UTF-8");
    }
}

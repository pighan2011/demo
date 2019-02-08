package com.bootdo.controller.app;

import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.PartOfBodyDao;
import com.bootdo.dao.app.SdSicknessRelDao;
import com.bootdo.dao.app.SymptomDao;
import com.bootdo.dao.app.SymptonDetailsDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/section")
public class SymptomController extends BaseController {

    @Resource
    SymptomDao symptomDao;

    @Resource
    SymptonDetailsDao symptonDetailsDao;

    @Resource
    PartOfBodyDao partOfBodyDao;

    @Resource
    SdSicknessRelDao sdSicknessRelDao;



    //智能化验，部位列表
    @PostMapping("/partList")
    public ResponseDO partList(@RequestParam(required = false) String partName, @RequestParam String hospitalId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("part", "%" + partName + "%");
            map.put("hospitalId", hospitalId);
            List<PartOfBodyDO> list = partOfBodyDao.list(map);
            list.forEach(partOfBodyDO -> {
                map.clear();
                map.put("pobId", partOfBodyDO.getId());
                List<SymptomDO> list1 = symptomDao.list(map);
                partOfBodyDO.setList(list1);
            });
            map.clear();
            map.put("partList", list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    //   根据部位查询对应的一级症状
    @PostMapping("/symptonList/{pobId}")
    public ResponseDO symptonList(@PathVariable Long pobId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("pobId", pobId);
            List<SymptomDO> list = symptomDao.list(map);
            map.clear();
            map.put("symptonList", list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    //  根据一级症状查询出二级症状
    @PostMapping("/detailsList/{sId}")
    public ResponseDO detailsList(@PathVariable Long sId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("sId", sId);
            List<SymptonDetailsDO> list = symptonDetailsDao.list(map);
            map.clear();
            map.put("detailsList", list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    // 根据选择的病症得出结果
    @PostMapping("/finalResult")
    public ResponseDO finalResult(String symptomArray, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            String[] array=symptomArray.split(",");
            Arrays.sort(array);
            StringBuilder sb = new StringBuilder();
            for (String integer : array) {
                sb.append(integer);
            }
            map.clear();
            map.put("collections", sb.toString());
            List<SdSicknessRelDO> list = sdSicknessRelDao.list(map);
            map.clear();
            map.put("finalResult", list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }


}

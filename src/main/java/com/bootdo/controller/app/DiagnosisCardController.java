package com.bootdo.controller.app;

import com.bootdo.controller.BaseController;
import com.bootdo.dao.user.DiagnosisCardDao;
import com.bootdo.dao.user.InvalidUserDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.vo.DiagnosisCardDO;
import com.bootdo.vo.InvalidUserDO;
import com.bootdo.vo.ResponseDO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/dc")
public class DiagnosisCardController extends BaseController {
    @Resource
    InvalidUserDao invalidUserDao;
    @Resource
    DiagnosisCardDao diagnosisCardDao;


    @PostMapping("/dcList")
    public ResponseDO dcList(HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        InvalidUserDO iud = invalidUserDao.getUserByPhone(loginName);
        map.clear();
        map.put("userId", iud.getId());
        List<DiagnosisCardDO> list = diagnosisCardDao.list(map);
        map.clear();

        map.put("dcList", list);
        return new ResponseDO(200, "请求成功", map);
    }

    @PostMapping("/dcDetails/{id}")
    public ResponseDO dcDetails(@PathVariable Long id, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            DiagnosisCardDO diagnosisCardDO=diagnosisCardDao.get(id);
            return new ResponseDO(200, "请求成功",diagnosisCardDO );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }
    @PostMapping("/deleteDc/{id}")
    public ResponseDO deleteDc(@PathVariable Long id, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }

        if (diagnosisCardDao.remove(id)>0){
            return new ResponseDO(200, "请求成功", null);
        }
        return new ResponseDO(500, "服务器错误", null);
    }

    @PostMapping("/saveDc")
    public ResponseDO saveDc(DiagnosisCardDO diagnosisCardDO, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        InvalidUserDO iud = invalidUserDao.getUserByPhone(loginName);
        map.clear();
        map.put("userId", iud.getId());
        if (diagnosisCardDao.list(map).size() == 3) {
            return new ResponseDO(201, "最多允许添加三张就诊卡", null);
        }
        diagnosisCardDO.setUserId(iud.getId());
        diagnosisCardDO.setStatus(1150);
        if (diagnosisCardDao.save(diagnosisCardDO) > 0) {
            return new ResponseDO(200, "请求成功", diagnosisCardDO);
        }
        return new ResponseDO(500, "服务器错误", null);
    }
}

package com.bootdo.controller.app;

import com.alibaba.fastjson.JSON;
import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.QuestionDao;
import com.bootdo.dao.app.UeDetailsDao;
import com.bootdo.dao.app.UserEvaluationDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.vo.QuestionDO;
import com.bootdo.vo.ResponseDO;
import com.bootdo.vo.UeDetailsDO;
import com.bootdo.vo.UserEvaluationDO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController extends BaseController {

    @Resource
    UserEvaluationDao userEvaluationDao;
    @Resource
    UeDetailsDao ueDetailsDao;
    @Resource
    QuestionDao questionDao;

    //  查询当前已启用得十个满意度配置项
    @PostMapping("/evaluationList")
    public ResponseDO savePic(HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("status", "1");
            List<QuestionDO> list = questionDao.list(map);
            map.clear();
            map.put("evaluationList", list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    //  新增用户评价
    @PostMapping("/saveEvaluation")
    public ResponseDO saveEvaluation(HttpServletRequest request) throws IOException {
        String wholeStr = getWholeTextFromRequest(request);
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            UserEvaluationDO ued = JSON.parseObject(wholeStr, UserEvaluationDO.class);
            ued.setCreateDate(new Date());
            userEvaluationDao.save(ued);
            if (ued.getList().size() > 0) {
                List<UeDetailsDO> list = ued.getList();
                list.forEach(ueDetailsDO -> {
                    ueDetailsDO.setUeId(ued.getId());
                    ueDetailsDao.save(ueDetailsDO);
                    map.clear();
                    map.put("id", ueDetailsDO.getName());
                    if (ueDetailsDO.getUdDesc().equals("0")) {
                        map.put("column", "down");
                    } else {
                        map.put("column", "up");
                    }
                    questionDao.modfiyCount(map);
                });
            }
            return new ResponseDO(200, "请求成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }
//  新增用户评价 安卓
    @PostMapping("/saveEvaluation_android")
    public ResponseDO saveEvaluation_android(String jsonData, HttpServletRequest request) throws IOException {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            UserEvaluationDO ued=JSON.parseObject(jsonData,UserEvaluationDO.class);
            ued.setCreateDate(new Date());
            userEvaluationDao.save(ued);
            if (ued.getList().size() > 0) {
                List<UeDetailsDO> list = ued.getList();
                list.forEach(ueDetailsDO -> {
                    ueDetailsDO.setUeId(ued.getId());
                    ueDetailsDao.save(ueDetailsDO);
                    map.clear();
                    map.put("id", ueDetailsDO.getName());
                    if ("0".equals(ueDetailsDO.getUdDesc())) {
                        map.put("column", "down");
                    } else {
                        map.put("column", "up");
                    }
                    questionDao.modfiyCount(map);
                });
            }
            return new ResponseDO(200, "请求成功", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }


}

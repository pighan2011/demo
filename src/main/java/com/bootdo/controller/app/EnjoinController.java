package com.bootdo.controller.app;

import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.TodayEnjoinDao;
import com.bootdo.dao.system.UserDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.vo.ResponseDO;
import com.bootdo.vo.TodayEnjoinDO;
import com.bootdo.vo.UserDO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/enjoin")
public class EnjoinController extends BaseController {

    @Resource
    TodayEnjoinDao todayEnjoinDao;

    @Resource
    UserDao userDao;

    //    今日医嘱
    @PostMapping("/todayEnjoin/{diagnosisId}/{deptId}")
    public ResponseDO savePic(@PathVariable String diagnosisId, @PathVariable String deptId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("diagnosisId", diagnosisId);
            map.put("deptId", deptId);
            List<TodayEnjoinDO> list = todayEnjoinDao.list(map);
            list.forEach(ted -> {
                UserDO userDO = userDao.get(ted.getUserId());
                ted.setUserDO(userDO);
            });
            map.clear();
            map.put("todayEnjoin", list);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", map);
    }
}

package com.bootdo.controller.app;

import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.AppointmentServiceDao;
import com.bootdo.dao.app.BuySetmealDao;
import com.bootdo.dao.app.UserAppointmentDoctorRelDao;
import com.bootdo.dao.user.InvalidUserDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.vo.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/userinfo")
public class UserController extends BaseController {
    @Resource
    InvalidUserDao invalidUserDao;
    @Resource
    UserAppointmentDoctorRelDao userAppointmentDoctorRelDao;
    @Resource
    BuySetmealDao buySetmealDao;
    @Resource
    AppointmentServiceDao appointmentServiceDao;

    //    我的医生
    @PostMapping("/myDoctorList/{userId}")
    public ResponseDO myDoctorList(@PathVariable long userId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("userId", userId);
            map.put("showFlag", "1");
            List<UserAppointmentDoctorRelDO> myDoctorList = invalidUserDao.myDoctorList(map);
            myDoctorList.forEach(uadr -> uadr.setFilesDO(getFilesVo(uadr.getPicId())));
            map.clear();
            map.put("myDoctorList", myDoctorList);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", map);
    }

    //   删除我的医生(逻辑删除，使其不可见)
    @PostMapping("/delMyDoctorRel/{id}")
    public ResponseDO delMyDoctorRel(@PathVariable long id, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            UserAppointmentDoctorRelDO uadr = new UserAppointmentDoctorRelDO();
            uadr.setId(id);
            uadr.setShowFlag(0);
            userAppointmentDoctorRelDao.update(uadr);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", null);
    }


    //   我的预约
    @PostMapping("/myAppointment/{userId}")
    public ResponseDO myAppointment(@PathVariable Long userId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
//           查询预约的医生
            map.clear();
            map.put("userId", userId);
            map.put("sort", "auadr.create_date");
            map.put("order", "desc");
            List<UserAppointmentDoctorRelDO> doctorList = invalidUserDao.myDoctorList(map);
            doctorList.forEach(uadr -> uadr.setFilesDO(getFilesVo(uadr.getPicId())));
            map.clear();
//          查询套餐
            map.put("userId", userId);
            List<SetmealDO> setmealList = buySetmealDao.setmealList(map);
//          上门服务
            List<AppointmentServiceDO> asList = appointmentServiceDao.list(map);

            map.clear();
            map.put("yuyueyisheng", doctorList);
            map.put("jiankangtijian", setmealList);
            map.put("shangmenfuwu", asList);

            return new ResponseDO(200, "请求成功", map);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    /**
     *  个人信息
     *
     *  */
    @PostMapping("/mySelfInfo/{id}")
    public ResponseDO mySelfInfo(@PathVariable Long id) {
        try {
            InvalidUserDO invalidUserDO = invalidUserDao.get(id);
            if (invalidUserDO.getPicId() != null) {
                invalidUserDO.setFilesDO(getFilesVo(invalidUserDO.getPicId()));
            }
            return new ResponseDO(200, "请求成功", invalidUserDO);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }


}

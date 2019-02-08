package com.bootdo.controller.app;


import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.UserAppointmentDoctorRelDao;
import com.bootdo.dao.system.UserDao;
import com.bootdo.dao.user.InvalidUserDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.util.WxUtil;
import com.bootdo.vo.InvalidUserDO;
import com.bootdo.vo.ResponseDO;
import com.bootdo.vo.UserAppointmentDoctorRelDO;
import com.bootdo.vo.UserDO;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserAppointmentDoctorController extends BaseController {
    @Value("${wx.pay.notifyUrl}")
    private String notifyUrl;

    private WxPayService wxService;
    @Autowired
    public UserAppointmentDoctorController(WxPayService wxService) {
        this.wxService = wxService;
    }
    @Resource
    UserAppointmentDoctorRelDao userAppointmentDoctorRelDao;
    @Resource
    UserDao userDao;
    @Resource
    InvalidUserDao invalidUserDao;

    /**
     *  微信支付：新增医生预约,至待支付状态
     * @param userAppointmentDoctorRelDO
     * @param httpServletRequest
     * @return
     * 1.在跳转到支付页面时，访问此接口，此时订单已经创建，并把含有id的订单信息返回给APP
     * 2.在支付页面，根据选择的方式，请求访问下单接口
     * 3.新建微信下单请求体，获取
     */
    @PostMapping("/createAppointmentOrder")
    public ResponseDO createAppointmentOrder(UserAppointmentDoctorRelDO userAppointmentDoctorRelDO, HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            if (userAppointmentDoctorRelDO.getDoctorId() == null) {
                return new ResponseDO(201, "医生ID不能为NULL", null);
            }
//            获取医生实体类
            UserDO doctor = userDao.get(userAppointmentDoctorRelDO.getDoctorId());
//            获取当前登录用户实体
            InvalidUserDO invalidUserDO = invalidUserDao.getUserByPhone(loginName);
            if (!invalidUserDO.getId().equals(userAppointmentDoctorRelDO.getUserId())) {
                return new ResponseDO(201, "传入的用户ID与登陆信息不相符", null);
            }
//            核对是否已经预约过该医生
            map.clear();
            map.put("diagnosisId", userAppointmentDoctorRelDO.getDiagnosisId());
            map.put("appointmentTime", getSdf().format(new Date()));
            map.put("doctorId", userAppointmentDoctorRelDO.getDoctorId());
            List<UserAppointmentDoctorRelDO> doctorList = userAppointmentDoctorRelDao.list(map);
            if (doctorList.size() > 0) {
                return new ResponseDO(201, "已经预约过此医生", null);
            }
//          核对是否达到医生预约上限
            map.clear();
            map.put("doctorId", userAppointmentDoctorRelDO.getDoctorId());
            map.put("appointmentTime", getSdf().format(new Date()));
            List<UserAppointmentDoctorRelDO> list = userAppointmentDoctorRelDao.list(map);
            if (list.size() == doctor.getMaxAppointment()) {
                return new ResponseDO(201, "医生预约已达上限", null);
            }
            userAppointmentDoctorRelDO.setNonceStr(getUuid());
            userAppointmentDoctorRelDO.setOutTradeNo(getUuid());
            userAppointmentDoctorRelDO.setPrice(doctor.getAppointmentCost());
            userAppointmentDoctorRelDO.setCreateDate(new Date());
//            1009待支付
            userAppointmentDoctorRelDO.setStatus(1009);
            if (userAppointmentDoctorRelDao.save(userAppointmentDoctorRelDO) > 0) {
                return new ResponseDO(200, "请求成功", userAppointmentDoctorRelDO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDO(500, "服务器错误", null);
    }

    /**
     * 支付：新增医生预约->请求统一下单
     * @param id 预约的id
     * @param type 微信为0，支付宝为1
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/AppointmentOrderWxPay/{id}/{type}")
    public ResponseDO AppointmentOrderWxPay(@PathVariable Long id, @PathVariable int type, HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            UserAppointmentDoctorRelDO uadr = userAppointmentDoctorRelDao.get(id);
            if (type == 0) {
                //            创建微信订单实体
                WxPayUnifiedOrderRequest request = WxUtil.getWxOrderVO();
                request.setBody("预约医生就诊");
                request.setNonceStr(uadr.getNonceStr());
                request.setOutTradeNo(uadr.getOutTradeNo());
                request.setTotalFee(WxUtil.Yuan2Fen(uadr.getPrice()));
                request.setNotifyUrl(notifyUrl + "/user/AppointmentOrderNotify");
                return new ResponseDO(200, "请求成功", wxService.createOrder(request));
            } else {
//                todo 支付宝支付
                return new ResponseDO(200, "请求成功", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDO(500, "服务器错误", null);
    }


    //    微信支付：新增医生预约回调函数
    @PostMapping("/AppointmentOrderNotify")
    public String AppointmentOrderNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = WxUtil.WxNotifyResponseMessage(request, response);
        if (resultMap != null && resultMap.get("return_code").equals("SUCCESS")) {
            String outTradeNo = resultMap.get("out_trade_no").toString();
            String nonceStr = resultMap.get("nonce_str").toString();
            map.clear();
            map.put("outTradeNo", outTradeNo);
            map.put("nonceStr", nonceStr);
            List<UserAppointmentDoctorRelDO> list = userAppointmentDoctorRelDao.list(map);
            if (list != null) {
                UserAppointmentDoctorRelDO uadr = list.get(0);
                if (WxUtil.Yuan2Fen(uadr.getPrice()).equals(Integer.valueOf(resultMap.get("total_fee").toString()))) {
                    uadr.setTransactionId(resultMap.get("transaction_id").toString());
//                    修改成已支付
                    uadr.setStatus(1000);
                    uadr.setPayType(0);
                    if (userAppointmentDoctorRelDao.update(uadr) > 0) {
                        return WxUtil.NOTIFY_RETURN_SUCCESS_CODE;
                    }
                }
            }
        }
        return WxUtil.NOTIFY_RETURN_FAIL_CODE;
    }


    /**
     * @param diagnosisId 就诊卡id
     * @return 预约叫号列表，有可能挂了很多号
     * 首先根据就诊卡号和今天日期，查询出他今天所有预约
     * 每一个预约都有不同的医生，（确保不会挂重复，在↑已经做判断）
     * 然后再根据医生ID，今天日期，查询每个医生当前的就诊列表，以及状态。
     */
    @PostMapping("/appointmentList/{diagnosisId}")
    public ResponseDO appointmentList(@PathVariable String diagnosisId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("diagnosisId", diagnosisId);
            map.put("appointmentTime", getSdf().format(new Date()));
            map.put("status", "1000");
//            该就诊卡的预约列表
            List<UserAppointmentDoctorRelDO> list = userAppointmentDoctorRelDao.list(map);
            for (UserAppointmentDoctorRelDO relDO : list) {
                map.clear();
                map.put("appointmentTime", getSdf().format(new Date()));
                map.put("doctorId", relDO.getDoctorId());
                List<UserAppointmentDoctorRelDO> list1 = userAppointmentDoctorRelDao.list(map);
                Map<String, Object> locationMap = new HashMap<>();
                for (int i = 0; i < list1.size(); i++) {
//                    下一个就诊人
                    if (locationMap.get("next") == null) {
                        if (list1.get(i).getStatus() == 1000) {
                            locationMap.put("next", list1.get(i));
                        }
                    }
//                    当前就诊人
                    if (list1.get(i).getStatus() == 1010) {
                        locationMap.put("current", list1.get(i));
                    }
//                   自己的位置
                    if (list1.get(i).getDiagnosisId().toString().equals(diagnosisId)) {
                        locationMap.put("location", i);
                    }
                }
                relDO.setMap(locationMap);
//                同引用，每次new，无需clear
            }
            map.clear();
            map.put("appointmentList", list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }

    }

    //    预约历史，状态为1020已完成的预约记录
    @PostMapping("/historyAppointmentList/{diagnosisId}/{deptId}")
    public ResponseDO historyAppointmentList(@PathVariable String diagnosisId, @PathVariable String deptId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("diagnosisId",diagnosisId);
            map.put("historyFlag",deptId);
            map.put("status","1020");
            List<UserAppointmentDoctorRelDO> list = userAppointmentDoctorRelDao.list(map);
            map.clear();
            map.put("historyAppointmentList",list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }

    }


}

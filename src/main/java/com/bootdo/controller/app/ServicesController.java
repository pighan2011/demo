package com.bootdo.controller.app;


import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.*;
import com.bootdo.dao.system.FilesDao;
import com.bootdo.dao.user.InvalidUserDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.util.WxUtil;
import com.bootdo.vo.*;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/services")
public class ServicesController extends BaseController {
    @Value("${wx.pay.notifyUrl}")
    private String notifyUrl;
    private WxPayService wxService;

    @Autowired
    public ServicesController(WxPayService wxService) {
        this.wxService = wxService;
    }

    @Resource
    VolunteerServiceDao volunteerServiceDao;
    @Resource
    ApplyServiceDao applyServiceDao;
    @Resource
    BedInfoDao bedInfoDao;
    @Resource
    InvalidUserDao invalidUserDao;
    @Resource
    AppointmentServiceDao appointmentServiceDao;
    @Resource
    CarApplyDao carApplyDao;
    @Resource
    FilesDao filesDao;
    @Value("${mobile.imgHttp}")
    private String imgHttp;

    // 医生上门，护士上门内容项目列表
    @PostMapping("/serviceList/{vsClass}")
    public ResponseDO serviceList(@PathVariable String vsClass, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("vsClass", vsClass);
            List<VolunteerServiceDO> list = volunteerServiceDao.list(map);
            list.forEach(this::accept);
            map.clear();
            map.put("serviceList", list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    // 医生上门，护士上门详细内容
    @PostMapping("/serviceDetails/{id}")
    public ResponseDO serviceDetails(@PathVariable Long id, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            VolunteerServiceDO volunteerServiceDO = volunteerServiceDao.get(id);
            accept(volunteerServiceDO);
            map.put("serviceDetails", volunteerServiceDO);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    // 购买医护上门服务
    @PostMapping("/buyService")
    public ResponseDO buyService(AppointmentServiceDO appointmentServiceDO, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            appointmentServiceDO.setNonceStr(getUuid());
            appointmentServiceDO.setOutTradeNo(getUuid());
            appointmentServiceDO.setStatus(1099);
            if (appointmentServiceDao.save(appointmentServiceDO) > 0) {
                return new ResponseDO(200, "请求成功", appointmentServiceDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDO(500, "服务器错误", null);

    }

    /**
     * 支付：医护上门->请求下单，
     *
     * @param id                 购买订单id
     * @param type               微信为0，支付宝为1
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/appointmentServicePay/{id}/{type}")
    public ResponseDO AppointmentOrderWxPay(@PathVariable Long id, @PathVariable int type, HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            AppointmentServiceDO asd = appointmentServiceDao.get(id);
            VolunteerServiceDO vsd = volunteerServiceDao.get(asd.getVsId());
            if (type == 0) {
                //            创建微信订单实体
                WxPayUnifiedOrderRequest request = WxUtil.getWxOrderVO();
                request.setBody("购买医护上门");
                request.setNonceStr(asd.getNonceStr());
                request.setOutTradeNo(asd.getOutTradeNo());
                request.setTotalFee(WxUtil.Yuan2Fen(vsd.getPrice()));
                request.setNotifyUrl(notifyUrl + "/services/appointmentServiceNotify");
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


    //    微信支付：购买体检套餐回调函数
    @PostMapping("/appointmentServiceNotify")
    public String AppointmentOrderNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = WxUtil.WxNotifyResponseMessage(request, response);
        if (resultMap != null && "SUCCESS".equals(resultMap.get("return_code"))) {
            String outTradeNo = resultMap.get("out_trade_no").toString();
            String nonceStr = resultMap.get("nonce_str").toString();
            map.clear();
            map.put("outTradeNo", outTradeNo);
            map.put("nonceStr", nonceStr);
            List<AppointmentServiceDO> list = appointmentServiceDao.list(map);
            if (list != null) {
                AppointmentServiceDO asd = list.get(0);
                VolunteerServiceDO vsd = volunteerServiceDao.get(asd.getVsId());
                if (WxUtil.Yuan2Fen(vsd.getPrice()).equals(Integer.valueOf(resultMap.get("total_fee").toString()))) {
                    asd.setTransactionId(resultMap.get("transaction_id").toString());
//                    修改成已支付
                    asd.setStatus(1100);
                    asd.setPayType(0);
                    if (appointmentServiceDao.update(asd) > 0) {
                        return WxUtil.NOTIFY_RETURN_SUCCESS_CODE;
                    }
                }
            }
        }
        return WxUtil.NOTIFY_RETURN_FAIL_CODE;
    }




    // 陪诊服务，申请会诊的添加
    @PostMapping("/saveApply")
    public ResponseDO saveApply(ApplyServiceDO applyServiceDO, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            InvalidUserDO invalidUserDO = invalidUserDao.getUserByPhone(loginName);
            applyServiceDO.setUserId(invalidUserDO.getId());
            applyServiceDO.setCreateTime(new Date());
            if (applyServiceDao.save(applyServiceDO) > 0) {
                return new ResponseDO(200, "请求成功", null);
            }
            return new ResponseDO(500, "服务器错误", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDO(500, "服务器错误", null);

    }

    // 床位信息
    @PostMapping("/bedList/{hospitalId}")
    public ResponseDO bedList(@PathVariable Long hospitalId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            List<BedInfoDO> list = bedInfoDao.list(null);
            map.clear();
            map.put("bedList", list);
            map.put("hospitalId", hospitalId);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDO(500, "服务器错误", null);

    }

    // 用车申请
    @PostMapping("/carApply")
    public ResponseDO carApply(CarApplyDO carApplyDO, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            if (carApplyDao.save(carApplyDO) > 0) {
                return new ResponseDO(200, "请求成功", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDO(500, "服务器错误", null);

    }

    private void accept(VolunteerServiceDO volunteerServiceDO) {
        FilesDO filesDO = filesDao.get(Long.valueOf(volunteerServiceDO.getIconFile()));
        String iconPath = imgHttp + filesDO.getFilePath() + filesDO.getFileName();
        volunteerServiceDO.setIconFile(iconPath);
        filesDO = filesDao.get(Long.valueOf(volunteerServiceDO.getHomePic()));
        iconPath = imgHttp + filesDO.getFilePath() + filesDO.getFileName();
        volunteerServiceDO.setHomePic(iconPath);
    }
}

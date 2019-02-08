package com.bootdo.controller.app;

import com.alibaba.fastjson.JSON;
import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.OrderDetailsDao;
import com.bootdo.dao.app.PaymentOrderDao;
import com.bootdo.dao.app.PicDianosisRelDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.util.WxUtil;
import com.bootdo.vo.OrderDetailsDO;
import com.bootdo.vo.PaymentOrderDO;
import com.bootdo.vo.PicDianosisRelDO;
import com.bootdo.vo.ResponseDO;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

//门诊订单管理
@RestController
@RequestMapping("/section")
public class SectionController extends BaseController {
    @Value("${wx.pay.notifyUrl}")
    private String notifyUrl;
    private WxPayService wxService;

    @Autowired
    public SectionController(WxPayService wxService) {
        this.wxService = wxService;
    }

    @Resource
    PaymentOrderDao paymentOrderDao;
    @Resource
    OrderDetailsDao orderDetailsDao;
    @Resource
    PicDianosisRelDao picDianosisRelDao;

    /**
     * 门诊化验单 按状态查询
     *
     * @param diagnosisId
     * @param status
     * @param deptId
     * @param request
     * @return
     */
    @PostMapping("/orderList/{diagnosisId}/{status}/{deptId}")
    public ResponseDO orderList(@PathVariable String diagnosisId, @PathVariable String status, @PathVariable String deptId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }

        try {
            map.clear();
            map.put("diagnosisId", diagnosisId);
            map.put("status", status);
            map.put("deptId", deptId);
            List<PaymentOrderDO> list = paymentOrderDao.list(map);
            list.forEach(paymentOrderDO -> {
                map.clear();
                map.put("poId", paymentOrderDO.getId());
                List<OrderDetailsDO> detailsList = orderDetailsDao.list(map);
                paymentOrderDO.setDetailsList(detailsList);
            });
            map.clear();
            map.put("orderList", list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDO(500, "服务器错误", null);
    }


    /**
     * 支付：门诊化验购买->请求下单，
     *
     * @param id                 购买订单id
     * @param type               微信为0，支付宝为1
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/orderPay/{id}/{type}")
    public ResponseDO AppointmentOrderWxPay(@PathVariable Long id, @PathVariable int type, HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            PaymentOrderDO pod = paymentOrderDao.get(id);

            if (type == 0) {
                //创建微信订单实体
                WxPayUnifiedOrderRequest request = WxUtil.getWxOrderVO();
                request.setBody("门诊缴费单");
                request.setNonceStr(pod.getNonceStr());
                request.setOutTradeNo(pod.getOutTradeNo());
                request.setTotalFee(WxUtil.Yuan2Fen(pod.getPayMoney()));
                request.setNotifyUrl(notifyUrl + "/section/orderPayNotify");
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


    //    微信支付：购买门诊缴费回调函数
    @PostMapping("/orderPayNotify")
    public String appointmentordernotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = WxUtil.WxNotifyResponseMessage(request, response);
        if (resultMap != null && resultMap.get("return_code").equals("SUCCESS")) {
            String outTradeNo = resultMap.get("out_trade_no").toString();
            String nonceStr = resultMap.get("nonce_str").toString();
            map.clear();
            map.put("outTradeNo", outTradeNo);
            map.put("nonceStr", nonceStr);
            List<PaymentOrderDO> list = paymentOrderDao.list(map);
            if (list != null) {
                PaymentOrderDO pod = list.get(0);
                if (WxUtil.Yuan2Fen(pod.getPayMoney()).equals(Integer.valueOf(resultMap.get("total_fee").toString()))) {
                    pod.setTransactionId(resultMap.get("transaction_id").toString());
//                    修改成已支付
                    pod.setStatus(1070);
                    pod.setPayType(0);
                    if (paymentOrderDao.update(pod) > 0) {
                        return WxUtil.NOTIFY_RETURN_SUCCESS_CODE;
                    }
                }
            }
        }
        return WxUtil.NOTIFY_RETURN_FAIL_CODE;
    }


    //获取图片列表  化验 。检查
    @RequestMapping(value = {"/picList/{diagnosisId}/{picClass}", "/picList/{diagnosisId}/{picClass}/{hosName}"}, method = RequestMethod.POST)
    public ResponseDO picList(@PathVariable String diagnosisId, @PathVariable String picClass, @PathVariable(required = false) String hosName, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("diagnosisId", diagnosisId);
            map.put("picClass", picClass);
            if (hosName!=null){
                map.put("hosName", "%" + hosName + "%");

            }
            List<PicDianosisRelDO> list = picDianosisRelDao.list(map);
            list.forEach(picDianosisRelDO -> picDianosisRelDO.setFilesDO(getFilesVo(picDianosisRelDO.getPicId())));
            map.clear();
            map.put("picList", list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }


    //    保存化验，检查报告的图片
    @PostMapping("/savePic")
    public ResponseDO savePic(HttpServletRequest request) throws IOException {

        String wholeStr = getWholeTextFromRequest(request);

        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            List<PicDianosisRelDO> list = JSON.parseArray(wholeStr, PicDianosisRelDO.class);
            list.forEach(p -> picDianosisRelDao.save(p));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", null);
    }

    //    保存化验，检查报告的图片
    @PostMapping("/deletePic/{id}")
    public ResponseDO deletePic(@PathVariable Long id, HttpServletRequest request) throws IOException {


        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            if (picDianosisRelDao.remove(id) > 0) {
                return new ResponseDO(200, "请求成功", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDO(500, "服务器错误", null);

    }
}

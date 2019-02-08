package com.bootdo.controller.app;


import com.alibaba.fastjson.JSON;
import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.AskDiagnoseDao;
import com.bootdo.dao.system.UserDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.util.WxUtil;
import com.bootdo.vo.*;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/askdiagnose")
public class AskDiagnoseController extends BaseController {
    @Value("${wx.pay.notifyUrl}")
    private String notifyUrl;

    private WxPayService wxService;

    @Autowired
    public AskDiagnoseController(WxPayService wxService) {
        this.wxService = wxService;
    }

    @Resource
    AskDiagnoseDao askDiagnoseDao;

    @Resource
    UserDao userDao;

    /**
     * @param request
     * @return
     * @throws IOException
     * @apiNote 问诊订单
     */
    @PostMapping("/saveAskDiagnose")
    public ResponseDO saveApply(AskDiagnoseDO askDiagnoseDO, HttpServletRequest request) throws IOException {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            askDiagnoseDO.setStatus(1020);
            askDiagnoseDO.setNonceStr(getUuid());
            askDiagnoseDO.setOutTradeNo(getUuid());
            askDiagnoseDao.save(askDiagnoseDO);
            return new ResponseDO(200, "请求成功", askDiagnoseDO);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    /**
     * 支付：住院缴费清单->请求下单，
     *
     * @param id                 申请id
     * @param type               微信为0，支付宝为1
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/askDiagnosePay/{id}/{type}")
    public ResponseDO hospitalizationOrderPay(@PathVariable Long id, @PathVariable int type, HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader(
                "Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            AskDiagnoseDO hpd = askDiagnoseDao.get(id);
            UserDO userDO = userDao.get(hpd.getDoctorId());
            if (type == 0) {
                //创建微信订单实体
                WxPayUnifiedOrderRequest request = WxUtil.getWxOrderVO();
                request.setBody("问诊订单");
                request.setNonceStr(hpd.getNonceStr());
                request.setOutTradeNo(hpd.getOutTradeNo());
                request.setTotalFee(WxUtil.Yuan2Fen(userDO.getInquiryCost()));
                request.setNotifyUrl(notifyUrl + "/askdiagnose/askDiagnosePayNotify");
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

    //    微信支付：住院缴费清单支付回调函数
    @PostMapping("/askDiagnosePayNotify")
    public String hospitalizationOrderPayNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = WxUtil.WxNotifyResponseMessage(request, response);
        if (resultMap != null && resultMap.get("return_code").equals("SUCCESS")) {
            String outTradeNo = resultMap.get("out_trade_no").toString();
            String nonceStr = resultMap.get("nonce_str").toString();
            map.clear();
            map.put("outTradeNo", outTradeNo);
            map.put("nonceStr", nonceStr);
            List<AskDiagnoseDO> list = askDiagnoseDao.list(map);
            if (list != null) {
                AskDiagnoseDO had = list.get(0);
                UserDO userDO = userDao.get(had.getDoctorId());

                if (WxUtil.Yuan2Fen(userDO.getInquiryCost()).equals(Integer.valueOf(resultMap.get("total_fee").toString()))) {
                    had.setTransactionId(resultMap.get("transaction_id").toString());
//                    修改成已支付
                    had.setStatus(1130);
                    had.setPayType(0);
                    if (askDiagnoseDao.update(had) > 0) {
                        return WxUtil.NOTIFY_RETURN_SUCCESS_CODE;
                    }
                }
            }
        }
        return WxUtil.NOTIFY_RETURN_FAIL_CODE;
    }

    public static void main(String[] args) {
        HospitalizationApplyDO had = new HospitalizationApplyDO();
        had.setDiagnosisId(133L);
        had.setDiagnosisName("就诊人姓名");
        had.setDeptId(12L);
        had.setDeptName("科室名称");
        had.setRoomClass("房间类型");
        had.setHosDay(23.00);
        List<HospitalizationAccompanyDO> list = new ArrayList<>();
        HospitalizationAccompanyDO ha = new HospitalizationAccompanyDO();
        ha.setName("马大三");
        ha.setPhone("14567890");
        list.add(ha);
        HospitalizationAccompanyDO ha1 = new HospitalizationAccompanyDO();
        ha1.setName("二脖子");
        ha1.setPhone("6453432345");
        list.add(ha1);
        had.setList(list);

        System.out.println(JSON.toJSONString(had));
    }
}

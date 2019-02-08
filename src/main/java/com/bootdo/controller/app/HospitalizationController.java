package com.bootdo.controller.app;


import com.alibaba.fastjson.JSON;
import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.HospitalizationAccompanyDao;
import com.bootdo.dao.app.HospitalizationApplyDao;
import com.bootdo.dao.app.HospitalizationPayListDao;
import com.bootdo.dao.app.OutHospitalDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.util.WxUtil;
import com.bootdo.vo.*;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.service.WxPayService;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hospitalization")
public class HospitalizationController extends BaseController {
    @Value("${wx.pay.notifyUrl}")
    private String notifyUrl;

    private WxPayService wxService;

    @Autowired
    public HospitalizationController(WxPayService wxService) {
        this.wxService = wxService;
    }
    @Resource
    HospitalizationApplyDao hospitalizationApplyDao;
    @Resource
    HospitalizationAccompanyDao hospitalizationAccompanyDao;
    @Resource
    HospitalizationPayListDao hospitalizationPayListDao;
    @Resource
    OutHospitalDao outHospitalDao;

    /**
     * @param request
     * @return
     * @throws IOException
     * @apiNote 申请住院  ios
     */
    @PostMapping("/saveApply")
    public ResponseDO saveApply(HttpServletRequest request) throws IOException {
        String wholeStr = getWholeTextFromRequest(request);
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            HospitalizationApplyDO hos = JSON.parseObject(wholeStr, HospitalizationApplyDO.class);
            map.put("diagnosisId", hos.getDiagnosisId());
            map.put("status", "1090");
            List<HospitalizationApplyDO> list=hospitalizationApplyDao.list(map);
            if (list.size()>0) {
                return new ResponseDO(201, "目前有住院申请进行中", null);
            }
            map.put("status", "1100");
            list=hospitalizationApplyDao.list(map);
            if (list.size()>0) {
                return new ResponseDO(201, "目前有住院申请进行中", null);
            }
            hos.setStatus(1090);
            hos.setNonceStr(getUuid());
            hos.setOutTradeNo(getUuid());
            hos.setApplyDate(new Date());
            hospitalizationApplyDao.save(hos);
            if (hos.getList() != null) {
                List<HospitalizationAccompanyDO> list1 = hos.getList();
                list1.forEach(hospitalizationAccompanyDO -> {
                    hospitalizationAccompanyDO.setHosId(hos.getId());
                    hospitalizationAccompanyDao.save(hospitalizationAccompanyDO);
                });
            }
            return new ResponseDO(200, "请求成功", hos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }
/**
     * @param request
     * @return
     * @throws IOException
     * @apiNote 申请住院  安卓
     */
    @PostMapping("/saveApply_android")
    public ResponseDO saveApplyAndroid(String jsonData, HttpServletRequest request) throws IOException {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            HospitalizationApplyDO hos=JSON.parseObject(jsonData,HospitalizationApplyDO.class);
            map.put("diagnosisId", hos.getDiagnosisId());
            map.put("status", "1090");
            List<HospitalizationApplyDO> list=hospitalizationApplyDao.list(map);

            if (list.size()>0) {
                return new ResponseDO(201, "目前有住院申请进行中", null);
            }
            map.put("status", "1100");
            list=hospitalizationApplyDao.list(map);
            if (list.size()>0) {
                return new ResponseDO(201, "目前有住院申请进行中", null);
            }
            hos.setStatus(1090);
            hos.setNonceStr(getUuid());
            hos.setOutTradeNo(getUuid());
            hos.setApplyDate(new Date());
            hospitalizationApplyDao.save(hos);
            if (hos.getList() != null) {
                List<HospitalizationAccompanyDO> list1 = hos.getList();
                list1.forEach(hospitalizationAccompanyDO -> {
                    hospitalizationAccompanyDO.setHosId(hos.getId());
                    hospitalizationAccompanyDao.save(hospitalizationAccompanyDO);
                });
            }
            return new ResponseDO(200, "请求成功", hos);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    /**
     * 住院缴费清单
     *
     * @param diagnosisId 就诊卡id
     * @param status      状态
     * @param deptId      医院id
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
            List<HospitalizationPayListDO> list = hospitalizationPayListDao.list(map);
            map.clear();
            map.put("orderList", list);
            return new ResponseDO(200, "请求成功", map);
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
    @PostMapping("/hospitalizationOrderPay/{id}/{type}")
    public ResponseDO hospitalizationOrderPay(@PathVariable Long id, @PathVariable int type, HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            HospitalizationPayListDO hpd = hospitalizationPayListDao.get(id);

            if (type == 0) {
                //创建微信订单实体
                WxPayUnifiedOrderRequest request = WxUtil.getWxOrderVO();
                request.setBody("住院缴费清单");
                request.setNonceStr(hpd.getNonceStr());
                request.setOutTradeNo(hpd.getOutTradeNo());
                request.setTotalFee(WxUtil.Yuan2Fen(hpd.getMoney()));
                request.setNotifyUrl(notifyUrl + "/hospitalization/hospitalizationOrderPayNotify");
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
    @PostMapping("/hospitalizationOrderPayNotify")
    public String hospitalizationOrderPayNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = WxUtil.WxNotifyResponseMessage(request, response);
        if (resultMap != null && "SUCCESS".equals(resultMap.get("return_code"))) {
            String outTradeNo = resultMap.get("out_trade_no").toString();
            String nonceStr = resultMap.get("nonce_str").toString();
            map.clear();
            map.put("outTradeNo", outTradeNo);
            map.put("nonceStr", nonceStr);
            List<HospitalizationPayListDO> list = hospitalizationPayListDao.list(map);
            if (list != null) {
                HospitalizationPayListDO had = list.get(0);
                if (WxUtil.Yuan2Fen(had.getMoney()).equals(Integer.valueOf(resultMap.get("total_fee").toString()))) {
                    had.setTransactionId(resultMap.get("transaction_id").toString());
//                    修改成已支付
                    had.setStatus(1140);
                    had.setPayType(0);
                    if (hospitalizationPayListDao.update(had) > 0) {
                        return WxUtil.NOTIFY_RETURN_SUCCESS_CODE;
                    }
                }
            }
        }
        return WxUtil.NOTIFY_RETURN_FAIL_CODE;
    }


    /**
     * 住院申请列表
     *
     * @param diagnosisId 就诊卡id
     * @param status      申请状态
     * @param deptId      医院id
     * @return
     */
    @RequestMapping(value = {"/applyList/{diagnosisId}/{deptId}/{status}", "/applyList/{diagnosisId}/{deptId}"}, method = RequestMethod.POST)
    public ResponseDO applyList(@PathVariable String diagnosisId, @PathVariable String deptId, @PathVariable(required = false) String status, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("diagnosisId", diagnosisId);
            map.put("status", status);
            map.put("applyList", deptId);
            List<HospitalizationApplyDO> list = hospitalizationApplyDao.list(map);
            if (list==null||list.size()==0){
                return new ResponseDO(201, "没有住院数据", null);

            }
            map.clear();
            list.forEach(ha -> {
                map.put("hosId", ha.getId());
                ha.setList(hospitalizationAccompanyDao.list(map));
            });
            map.clear();
//            每次查询出来的结果最后一个
            map.put("applyList", list.get(list.size() - 1));
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    /**

     * 支付：住院申请->请求下单，
     *
     * @param id                 申请id
     * @param type               微信为0，支付宝为1
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/hospitalizationApplyPay/{id}/{type}")
    public ResponseDO AppointmentOrderWxPay(@PathVariable Long id, @PathVariable int type, HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            HospitalizationApplyDO had = hospitalizationApplyDao.get(id);
            if (had.getStatus() != 1100) {
                return new ResponseDO(201, "订单已支付完成", null);
            }
            if (had.getMoney() == null) {
                return new ResponseDO(201, "金额错误，请联系管理员", null);
            }
            if (type == 0) {
                //创建微信订单实体
                WxPayUnifiedOrderRequest request = WxUtil.getWxOrderVO();
                request.setBody("住院申请");
                request.setNonceStr(had.getNonceStr());
                request.setOutTradeNo(had.getOutTradeNo());
                request.setTotalFee(WxUtil.Yuan2Fen(had.getMoney().add(had.getAccompanyPrice())));
                request.setNotifyUrl(notifyUrl + "/hospitalization/hospitalizationApplyPayNotify");
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

    //    微信支付：住院申请支付回调函数
    @PostMapping("/hospitalizationApplyPayNotify")
    public String AppointmentOrderNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = WxUtil.WxNotifyResponseMessage(request, response);
        if (resultMap != null && "SUCCESS".equals(resultMap.get("return_code"))) {
            String outTradeNo = resultMap.get("out_trade_no").toString();
            String nonceStr = resultMap.get("nonce_str").toString();
            map.clear();
            map.put("outTradeNo", outTradeNo);
            map.put("nonceStr", nonceStr);
            List<HospitalizationApplyDO> list = hospitalizationApplyDao.list(map);
            if (list != null) {
                HospitalizationApplyDO had = list.get(0);
                if (WxUtil.Yuan2Fen(had.getMoney().add(had.getAccompanyPrice())).equals(Integer.valueOf(resultMap.get("total_fee").toString()))) {
                    had.setTransactionId(resultMap.get("transaction_id").toString());
//                    修改成已支付
                    had.setStatus(1110);
                    had.setPayType(0);
                    if (hospitalizationApplyDao.update(had) > 0) {
                        return WxUtil.NOTIFY_RETURN_SUCCESS_CODE;
                    }
                }
            }
        }
        return WxUtil.NOTIFY_RETURN_FAIL_CODE;
    }

    /**
     * 申请出院、
     * 需要判断该就诊卡下 有一张 状态为已付款的住院申请才可以  。
      * @param id
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/saveOutHos")
    public ResponseDO deletePic(OutHospitalDO outHospitalDO, HttpServletRequest request) throws IOException {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("diagnosisId",outHospitalDO.getDiagnosisId());
            map.put("status","1110");
            List<HospitalizationApplyDO> list=hospitalizationApplyDao.list(map);
            if (list!=null){
                HospitalizationApplyDO had;
                try {
                    had = list.get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseDO(201, "不存在住院记录", null);
                }
                outHospitalDO.setStatus(1140);
                outHospitalDO.setInDate(had.getHosDate().toString());
                if (outHospitalDao.save(outHospitalDO) > 0) {
                    had.setStatus(1111);
                    if (hospitalizationApplyDao.update(had)>0){
                        return new ResponseDO(200, "请求成功", outHospitalDO);
                    }
                    return new ResponseDO(201, "住院信息更新失败", null);
                }
            }else {
                return new ResponseDO(201, "不存在住院记录", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDO(500, "服务器错误", null);

    }
}

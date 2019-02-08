package com.bootdo.controller.app;

import com.bootdo.controller.BaseController;
import com.bootdo.dao.app.BuySetmealDao;
import com.bootdo.dao.app.SetmealDao;
import com.bootdo.dao.app.SetmealDetailsDao;
import com.bootdo.util.JWTUtil;
import com.bootdo.util.WxUtil;
import com.bootdo.vo.BuySetmealDO;
import com.bootdo.vo.ResponseDO;
import com.bootdo.vo.SetmealDO;
import com.bootdo.vo.SetmealDetailsDO;
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
@RequestMapping("/setmeal")
public class SetmealController extends BaseController {
    @Value("${wx.pay.notifyUrl}")
    private String notifyUrl;

    private WxPayService wxService;

    @Autowired
    public SetmealController(WxPayService wxService) {
        this.wxService = wxService;
    }

    @Resource
    SetmealDao setmealDao;

    @Resource
    SetmealDetailsDao setmealDetailsDao;

    @Resource
    BuySetmealDao buySetmealDao;


    //套餐列表
    @PostMapping("/setmealList/{deptId}")
    public ResponseDO savePic(@PathVariable String deptId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            map.clear();
            map.put("deptId", deptId);
            List<SetmealDO> list = setmealDao.list(map);
            list.forEach(setmealDO -> setmealDO.setFilesDO(getFilesVo(Long.valueOf(setmealDO.getHomePage()))));
            map.clear();
            map.put("setmealList", list);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    //    套餐详情
    @PostMapping("/setmealDetails/{setmealId}")
    public ResponseDO setmealDetails(@PathVariable Long setmealId, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            SetmealDO setmealDO = setmealDao.get(setmealId);
            setmealDO.setFilesDO(getFilesVo(Long.valueOf(setmealDO.getHomePage())));
            map.clear();
            map.put("sId", setmealId);
            List<SetmealDetailsDO> list = setmealDetailsDao.list(map);
            setmealDO.setList(list);
            map.clear();
            map.put("setmealDetails", setmealDO);
            return new ResponseDO(200, "请求成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    //    购买套餐
    @PostMapping("/buySetmeal")
    public ResponseDO buySetmeal(BuySetmealDO buySetmealDO, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            buySetmealDO.setNonceStr(getUuid());
            buySetmealDO.setOutTradeNo(getUuid());
            buySetmealDO.setCreateDate(new Date());
            buySetmealDO.setStatus(1079);
            if (buySetmealDao.save(buySetmealDO) > 0) {
                return new ResponseDO(200, "请求成功", buySetmealDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseDO(500, "服务器错误", null);

    }


    /**
     * 支付：套餐购买->请求下单，
     *
     * @param id                 购买订单id
     * @param type               微信为0，支付宝为1
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/buySetMealPay/{id}/{type}")
    public ResponseDO AppointmentOrderWxPay(@PathVariable Long id, @PathVariable int type, HttpServletRequest httpServletRequest) {
        String auth = httpServletRequest.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            BuySetmealDO bsd = buySetmealDao.get(id);
            SetmealDO setmealDO = setmealDao.get(bsd.getSetmealId());
            if (type == 0) {
                //            创建微信订单实体
                WxPayUnifiedOrderRequest request = WxUtil.getWxOrderVO();
                request.setBody("购买体检套餐");
                request.setNonceStr(bsd.getNonceStr());
                request.setOutTradeNo(bsd.getOutTradeNo());
                request.setTotalFee(WxUtil.Yuan2Fen(setmealDO.getNewPrice()));
                request.setNotifyUrl(notifyUrl + "/setmeal/buySetMealNotify");
                System.out.println(request);
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
    @PostMapping("/buySetMealNotify")
    public String AppointmentOrderNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = WxUtil.WxNotifyResponseMessage(request, response);
        if (resultMap != null && resultMap.get("return_code").equals("SUCCESS")) {
            String outTradeNo = resultMap.get("out_trade_no").toString();
            String nonceStr = resultMap.get("nonce_str").toString();
            map.clear();
            map.put("outTradeNo", outTradeNo);
            map.put("nonceStr", nonceStr);
            List<BuySetmealDO> list = buySetmealDao.list(map);
            if (list != null) {
                BuySetmealDO bsd = list.get(0);
                SetmealDO sd = setmealDao.get(bsd.getSetmealId());
                if (WxUtil.Yuan2Fen(sd.getNewPrice()).equals(Integer.valueOf(resultMap.get("total_fee").toString()))) {
                    bsd.setTransactionId(resultMap.get("transaction_id").toString());
//                    修改成已支付
                    bsd.setStatus(1080);
                    bsd.setPayType(0);
                    if (buySetmealDao.update(bsd) > 0) {
                        return WxUtil.NOTIFY_RETURN_SUCCESS_CODE;
                    }
                }
            }
        }
        return WxUtil.NOTIFY_RETURN_FAIL_CODE;
    }

}


package com.bootdo.controller.app;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.bootdo.config.AlipayProperties;
import com.bootdo.controller.BaseController;
import com.bootdo.dao.user.InvalidUserDao;
import com.bootdo.easemob.entity.EasemobResponse;
import com.bootdo.util.*;
import com.bootdo.vo.InvalidUserDO;
import com.bootdo.vo.ResponseDO;
import io.swagger.client.model.Msg;
import io.swagger.client.model.UserName;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class LoginController extends BaseController {
    @Resource
    InvalidUserDao invalidUserDao;

    @Resource
    AlipayProperties alipayProperties;
    @Resource
    private AlipayClient alipayClient;

    @PostMapping("/sendCode/{phone}")
    public ResponseDO sendCode(@PathVariable String phone) {
        String code = String.valueOf(MessageUtil.getCode());
        try {
            RedisUtil.getRedis().set(phone + (new SimpleDateFormat("yyyyMMddhh").format(new Date())), code);
            MessageUtil.sendMessage(phone, "1", MessageUtil.getArray(code));

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", code);
    }

    @PostMapping("/register/{code}/{phone}/{password}")
    public ResponseDO verificationCode(@PathVariable String code, @PathVariable String phone, @PathVariable String password) {
        try {

            String v = null;
            try {
                v = RedisUtil.getRedis().get(phone + (new SimpleDateFormat("yyyyMMddhh").format(new Date())));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseDO(201, "验证码超时", null);
            }
            if (!v.equals(code)) {
                return new ResponseDO(201, "验证码错误", null);
            }
            map.clear();
            map.put("phone", phone);
            if (invalidUserDao.list(map).size() > 0) {
                return new ResponseDO(201, "手机号重复", null);
            }
            String passWord = MD5Utils.encrypt(password);
            InvalidUserDO ued = new InvalidUserDO();
            ued.setPhone(phone);
            ued.setPassword(passWord);
            ued.setRyToken(getUuid());
            invalidUserDao.save(ued);
            EasemobResponse easemobResponse = EasemobUtil.RegisterUser(ued.getRyToken(), passWord);
            if (easemobResponse.getError() != null) {
                return new ResponseDO(201, "绑定环信出错", easemobResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", "success");
    }


    @PostMapping("/PwdLogin/{phone}/{password}")
    public ResponseDO login(@PathVariable("phone") String phone, @PathVariable("password") String password) {
        try {
            InvalidUserDO iud = invalidUserDao.getUserByPhone(phone);
            if (iud == null) {
                return new ResponseDO(201, "该手机号未注册", null);
            }
            if (iud.getPassword().equals(MD5Utils.encrypt(password))) {
                if (iud.getPicId() != null) {
                    iud.setFilesDO(getFilesVo(iud.getPicId()));
                }
                map.clear();
                map.put("Access_Token", JWTUtil.sign(phone, password));
                map.put("user", iud);
                return new ResponseDO(200, "请求成功", map);
            } else {
                return new ResponseDO(201, "密码错误", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    @PostMapping("/CodeLogin/{code}/{phone}")
    public ResponseDO CodeLogin(@PathVariable("code") String code, @PathVariable("phone") String phone) {
        try {
            String v = null;
            try {
                v = RedisUtil.getRedis().get(phone + (new SimpleDateFormat("yyyyMMddhh").format(new Date())));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseDO(201, "验证码超时", null);
            }
            if (code.equals(v)) {
                InvalidUserDO invalidUserDO = invalidUserDao.getUserByPhone(phone);
                if (invalidUserDO == null) {
                    return new ResponseDO(201, "该手机号未注册", null);
                }
                if (invalidUserDO.getPicId() != null) {
                    invalidUserDO.setFilesDO(getFilesVo(invalidUserDO.getPicId()));
                }

                map.clear();
                map.put("Access_Token", JWTUtil.sign(phone, code));
                map.put("user", invalidUserDO);
                return new ResponseDO(200, "请求成功", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(201, "验证码错误", null);
    }

    //        修改用户信息
    @PostMapping("/UpdateUser")
    public ResponseDO UpdateUser(InvalidUserDO invalidUserDO, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }

        try {
            if (invalidUserDO.getPhone() != null) {
                map.clear();
                map.put("phone", invalidUserDO.getPhone());
                List<InvalidUserDO> list = invalidUserDao.list(map);
                if (list.size() > 0) {
                    return new ResponseDO(201, "手机号已存在", null);
                }
            }
            if (invalidUserDao.update(invalidUserDO) > 0) {
                if (invalidUserDO.getPicId() != null) {
                    invalidUserDO.setFilesDO(getFilesVo(invalidUserDO.getPicId()));
                }
                map.clear();
                map.put("user", invalidUserDO);
                return new ResponseDO(200, "请求成功", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(500, "服务器错误", null);

    }

    @PostMapping("/ModifyPwd/{id}/{oldPwd}/{newPwd}/{reNewPwd}")
    public ResponseDO ModifyPwd(@PathVariable Long id, @PathVariable String oldPwd, @PathVariable String newPwd, @PathVariable String reNewPwd, HttpServletRequest request) {
        String auth = request.getHeader("Access_Token");
        String loginName = JWTUtil.getUsername(auth);
        if (loginName == null) {
            return new ResponseDO(201, "身份无效", null);
        }
        try {
            if (!newPwd.equals(reNewPwd)) {
                return new ResponseDO(201, "两次密码不相同", null);
            }
            InvalidUserDO iud = invalidUserDao.get(id);
            if (iud.getPassword().equals(MD5Utils.encrypt(oldPwd))) {
                iud.setPassword(MD5Utils.encrypt(newPwd));
                invalidUserDao.update(iud);
                return new ResponseDO(200, "请求成功", iud);

            }
            return new ResponseDO(201, "原密码不匹配", null);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }

    }

    @PostMapping("/CheckPhoneExist/{phone}")
    public ResponseDO CheckPhoneExist(@PathVariable String phone) {
        try {
            if (invalidUserDao.getUserByPhone(phone) != null) {
                return new ResponseDO(200, "存在此用户", null);
            } else {
                return new ResponseDO(201, "不存在此用户", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    @PostMapping("/ForgotPwd/{code}/{phone}/{newPwd}/{reNewPwd}")
    public ResponseDO ForgotPwd(@PathVariable String code, @PathVariable String phone, @PathVariable String newPwd, @PathVariable String reNewPwd) {
        try {
            String v = null;
            try {
                v = RedisUtil.getRedis().get(phone + (new SimpleDateFormat("yyyyMMddhh").format(new Date())));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseDO(201, "验证码超时", null);
            }
            if (!code.equals(v)) {
                return new ResponseDO(201, "验证码错误", null);
            }
            if (!newPwd.equals(reNewPwd)) {
                return new ResponseDO(201, "两次密码不一致", null);
            }
            InvalidUserDO invalidUserDO = invalidUserDao.getUserByPhone(phone);
            if (invalidUserDO == null) {
                return new ResponseDO(201, "该手机号未注册", null);
            }
            invalidUserDO.setPassword(MD5Utils.encrypt(newPwd));
            if (invalidUserDao.update(invalidUserDO) > 0) {
                return new ResponseDO(200, "请求成功", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(500, "服务器错误", null);
    }

    @PostMapping("/wxLogin/{openId}")
    public ResponseDO wxLogin(@PathVariable String openId) {
        try {
            InvalidUserDO invalidUserDO = invalidUserDao.getUserByOpenId(openId);
            if (invalidUserDO != null) {
                map.clear();
                map.put("Access_Token", JWTUtil.sign(invalidUserDO.getPhone(), invalidUserDO.getPassword()));
                map.put("user", invalidUserDO);
                return new ResponseDO(200, "请求成功", map);
            }
            return new ResponseDO(201, "该微信未注册", null);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
    }

    @PostMapping("/wxRegister")
    public ResponseDO verificationCode(InvalidUserDO invalidUserDO) {
        try {

            String v = null;
            try {
                v = RedisUtil.getRedis().get(invalidUserDO.getPhone() + (new SimpleDateFormat("yyyyMMddhh").format(new Date())));
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseDO(201, "验证码超时", null);
            }
            if (!v.equals(invalidUserDO.getCode())) {
                return new ResponseDO(201, "验证码错误", null);
            }
            map.clear();
            InvalidUserDO invalidUserDO1 = invalidUserDao.getUserByPhone(invalidUserDO.getPhone());
            if (invalidUserDO1 != null) {
                invalidUserDO1.setOpenId(invalidUserDO.getOpenId());
                if (invalidUserDao.update(invalidUserDO1) > 0) {
                    return new ResponseDO(200, "绑定成功", null);
                }
            }
            String password = MD5Utils.encrypt(invalidUserDO.getPassword());
            invalidUserDO.setPassword(password);
            invalidUserDO.setRyToken(getUuid());
            invalidUserDao.save(invalidUserDO);
            EasemobResponse easemobResponse = EasemobUtil.RegisterUser(invalidUserDO.getRyToken(), invalidUserDO.getPassword());
            if (easemobResponse.getError() != null) {
                return new ResponseDO(201, "绑定环信出错", easemobResponse);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDO(500, "服务器错误", null);
        }
        return new ResponseDO(200, "请求成功", null);
    }

    @GetMapping("/huanxin")
    public EasemobResponse huanxin(MultipartFile file) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        File file1=new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileUtils.copyInputStreamToFile(file.getInputStream(),file1);
        return EasemobUtil.uploadFile(file1);
    }

    @GetMapping("/info")
    public ResponseDO info() throws AlipayApiException {
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式
        AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        //业务参数传入,可以传很多，参考API
        //model.setPassbackParams(URLEncoder.encode(request.getBody().toString())); //公用参数（附加数据）
        model.setBody("PINGGOO");            //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
        model.setSubject("iphone61g");         //商品名称
        model.setOutTradeNo(getUuid());      //商户订单号(自动生成)
        // model.setTimeoutExpress("30m");     //交易超时时间
        model.setTotalAmount("1");     //支付金额
        model.setProductCode("QUICK_MSECURITY_PAY");      //销售产品码（固定值）
        ali_request.setBizModel(model);
        ali_request.setNotifyUrl(alipayProperties.getNotifyUrl() + "/notifyUrl");    //异步回调地址（后台）
        ali_request.setReturnUrl(alipayProperties.getReturnUrl());   //同步回调地址（APP）

        // 这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(ali_request); //返回支付宝订单信息(预处理)
        String a = alipayTradeAppPayResponse.getBody();//就是orderString 可以直接给APP请求，无需再做处理。
//        this.createAlipayMentOrder(alipaymentOrder);//创建新的商户支付宝订单

        return new ResponseDO(200, "", a);
    }

    @GetMapping("/notifyUrl")
    public String notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> map = AliPayUtil.notifyMessage2Map(request, response, alipayProperties.getCharset());
        boolean verifyResult = AlipaySignature.rsaCheckV1(map, alipayProperties.getAlipayPublicKey(), alipayProperties.getCharset(), "RSA2");
        System.out.println(map);
        System.out.println(verifyResult);
        if (verifyResult) {
            //验证成功
            //请在这里加上商户的业务逻辑程序代码，如保存支付宝交易号
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
            return "wapPaySuccess";
        } else {
            return "wapPayFail";
        }
    }
}

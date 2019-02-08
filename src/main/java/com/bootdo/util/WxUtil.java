package com.bootdo.util;

import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import me.chanjar.weixin.common.util.XmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

public class WxUtil  {
    public static final String NOTIFY_RETURN_SUCCESS_CODE = "<xml> <return_code><![CDATA[SUCCESS]]></return_code> " +
            "  <return_msg><![CDATA[OK]]></return_msg> " +
            "</xml>";
    public static final String NOTIFY_RETURN_FAIL_CODE = "<xml> <return_code><![CDATA[FAIL]]></return_code> " +
            "  <return_msg><![CDATA[NO]]></return_msg> " +
            "</xml>";

    public static WxPayUnifiedOrderRequest getWxOrderVO(){
        WxPayUnifiedOrderRequest request=new WxPayUnifiedOrderRequest();
        request.setOutTradeNo(UUID.randomUUID().toString().replace("-",""));
        request.setNonceStr(UUID.randomUUID().toString().replace("-",""));
        request.setSpbillCreateIp("0.0.0.0");
        request.setTradeType("APP");
        return request;
    }
    public static Integer Yuan2Fen(Object yuan) {
        return new BigDecimal(String.valueOf(yuan)).movePointRight(2).intValue();
    }
    public static Map<String, Object> WxNotifyResponseMessage(HttpServletRequest request, HttpServletResponse response) {
        String resXml = "";
        InputStream inStream;
        try {
            inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            // 获取微信调用我们notify_url的返回信息
            String result = new String(outSteam.toByteArray(), StandardCharsets.UTF_8);
            // 关闭流
            outSteam.close();
            inStream.close();

            // xml转换为map
            return XmlUtils.xml2Map(result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 处理业务完毕
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}


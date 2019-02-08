package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 问诊表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2019-01-08 15:56:06
 */
@Data
public class AskDiagnoseDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //问诊类型
    private Integer adType;
    //用户id
    private Long userId;
    //医生id
    private Long doctorId;
    //状态
    private Integer status;
//    图片id
    private Long picId;
    //第三方订单号
    private String transactionId;
    //随机字符串
    private String nonceStr;
    //本地订单号
    private String outTradeNo;
    //支付方式
    private Integer payType;

}

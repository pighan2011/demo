package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 购买套餐预约表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-19 11:04:43
 */
@Data
public class BuySetmealDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //用户id
    private Long userId;
    //套餐id
    private Long setmealId;
    //购买时间
    private Date createDate;
    //状态
    private Integer status;
    //    就诊卡id
    private Long diagnosisId;
    //    就诊卡姓名
    private Long diagnosisName;
    //订单号
    private String transactionId;
    //随机字符串
    private String nonceStr;
    //订单号
    private String outTradeNo;
    //    支付方式
    private int payType;

}

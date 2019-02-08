package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 门诊缴费单
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:15
 */
@Data
public class PaymentOrderDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //缴费名称
    private String payName;
    //缴费类别
    private String payClass;
    //缴费总价
    private BigDecimal payMoney;
    //状态
    private Integer status;
    //医生id
    private Long doctorId;
    //医生名称
    private String doctorName;
    //就诊卡id
    private Long diagnosisId;
    //患者id
    private Long userId;
    //创建时间
    private Date createName;
//    详细内容
    private List<OrderDetailsDO> detailsList;

    private String name;

    private Integer age;

    private Integer sex;

    private String JsonData;

    //订单号
    private String transactionId;
    //随机字符串
    private String nonceStr;
    //订单号
    private String outTradeNo;
    //    支付方式
    private int payType;
}

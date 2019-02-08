package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 住院缴费清单
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-26 16:52:02
 */
@Data
public class HospitalizationPayListDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //缴费名称
    private String name;
    //缴费日期
    private Date payDate;
    //缴费金额
    private BigDecimal money;
    //患者id
    private Long userId;
    //就诊卡id
    private Long diagnosisId;
    //状态
    private Integer status;
    //创建日期
    private Date createDate;
    //创建人
    private String createBy;

    private Long hosId;
    //订单号
    private String transactionId;
    //随机字符串
    private String nonceStr;
    //订单号
    private String outTradeNo;
    //    支付方式
    private int payType;
}

package com.bootdo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 申请住院
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-23 10:20:41
 */
@Data
public class HospitalizationApplyDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //患者id
    private Long userId;
    //就诊卡id
    private Long diagnosisId;
    //就诊人姓名
    private String diagnosisName;
    //科室id
    private Long deptId;
    //科室名称
    private String deptName;
    //病房类型
    private String roomClass;
    //状态
    private Integer status;
    //申请日期
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date applyDate;
    //住院日期
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date hosDate;
    //住院天数
    private Double hosDay;
    //金额
    private BigDecimal money;
    //订单号
    private String transactionId;
    //随机字符串
    private String nonceStr;
    //订单号
    private String outTradeNo;
    //    支付方式
    private int payType;

    private String name;

    private Integer age;

    private Integer sex;
    private BigDecimal accompanyPrice;


    private  List<HospitalizationAccompanyDO> list;


    public BigDecimal getAccompanyPrice() {
        return accompanyPrice;
    }

    public void setAccompanyPrice(BigDecimal accompanyPrice) {
        this.accompanyPrice = accompanyPrice;
    }
}

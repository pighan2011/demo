package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 预约服务列表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-12 10:33:01
 */
@Data
public class AppointmentServiceDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //预约项目id
    private Long vsId;

    //用户id
    private Long userId;
    //姓名
    private String name;
    //上门时间
    private String appotionmentTime;
    //服务地址
    private String address;
    //身份证号
    private String idCard;
    //备注
    private String remark;
    //状态
    private Integer status;

    private String userName;
    //项目名称
    private String vsName;
    //医院名称
    private String deptName;
    //价格
    private BigDecimal price;
    //    类别
    private int vsClass;
    //订单号
    private String transactionId;
    //随机字符串
    private String nonceStr;
    //订单号
    private String outTradeNo;
    //    支付方式
    private int payType;

}

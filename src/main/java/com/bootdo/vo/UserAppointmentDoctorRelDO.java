package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;


/**
 * 患者预约医生表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 18:00:12
 */
@Data
public class UserAppointmentDoctorRelDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //患者id
    private Long userId;
    //就诊卡id
    private Long diagnosisId;
    //就诊人姓名
    private String diagnosisName;
    //医院id
    private Long deptId;
    //医院名称
    private String deptName;
    //    p
    private String hospitalName;
    //服务费用
    private BigDecimal price;
    //预约项目
    private String name;
    //预约时间
    private String appointmentTime;
    //创建时间
    private Date createDate;
    //医生id
    private Long doctorId;
    //医生姓名
    private String doctorName;
    //状态
    private Integer status;
    //我的医生列表页中是否可见
    private Integer showFlag;
    // 擅长
    private String goodAt;
    //头衔
    private String jobTitle;
    //年龄
    private Integer age;
    //性别
    private Integer sex;

    private String checkName;
    //身份证
    private String idCard;
    //医保卡
    private String medicare;
    //用户名
    private String username;
    //手机号
    private String mobile;

    private Long picId;

    private FilesDO filesDO;
    //微信订单号
    private String transactionId;
    //随机字符串
    private String nonceStr;
    //订单号
    private String outTradeNo;
    //    支付方式
    private int payType;

    private Map<String, Object> map;

}

package com.bootdo.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class UserDO implements Serializable {
    private static final long serialVersionUID = 1L;
    //
    private Long userId;
    // 用户名
    private String username;
    // 用户真实姓名
    private String name;
    // 密码
    private String password;
    // 部门
    private Long deptId;
    // 部门名称
    private String deptName;
    // 邮箱
    private String email;
    // 手机号
    private String mobile;
    // 状态 0:禁用，1:正常
    private Integer status;
    // 创建用户id
    private Long userIdCreate;
    // 创建时间
    private Date gmtCreate;
    // 修改时间
    private Date gmtModified;
    //角色
    private List<Long> roleIds;
    //性别
    private Long sex;
    //出身日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    //图片ID
    private String picId;
    //现居住地
    private String liveAddress;
    //爱好
    private String hobby;
    //省份
    private String province;
    //所在城市
    private String city;
    //所在地区
    private String district;
    //工作年限
    private Integer workYears;
    //自我介绍
    private String introduce;
    //擅长
    private String goodAt;
    //经历
    private String experiences;
    //职称
    private String jobTitle;
    //标签
    private String tags;
    //预约上限
    private Integer maxAppointment;
    //微信的唯一id
    private String openId;
    //融云token
    private String ryToken;
    //是否可预约0否1是
    private Integer appointmentFlag;
    //    标签
    private List<String> tagsArray;
    //    当前所选日期
    private String today;
    //    会诊费用
    private BigDecimal appointmentCost;

    private int userType;

    private FilesDO filesDO;
    //    问诊费用
    private BigDecimal inquiryCost;

}

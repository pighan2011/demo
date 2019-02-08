package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 申请会诊表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-12 18:01:53
 */
@Data
public class ApplyServiceDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //用户id
    private Long userId;
    //服务类别
    private String asClass;
    //姓名
    private String name;
    //年龄
    private Integer age;
    //性别
    private Integer sex;
    //联系电话
    private String phone;
    //会诊时间/陪诊时间
    private String meetTime;
    //创建时间
    private Date createTime;
    //病症
    private String symptom;
    //病床号码
    private String bedNum;

    private String reason;
//    医院id
    private Long hospitalId;

}

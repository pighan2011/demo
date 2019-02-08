package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 就诊卡信息
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:31:22
 */
@Data
public class DiagnosisCardDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //姓名
    private String name;
    //性别
    private Integer sex;
    //身份证
    private String idCard;
    //年龄
    private Integer age;
    //手机号
    private String phone;
    //与本人关系
    private String relition;
    //医保卡号
    private String medicare;
    //关联用户id
    private Long userId;
    //状态
    private Integer status;

    private String userName;

}

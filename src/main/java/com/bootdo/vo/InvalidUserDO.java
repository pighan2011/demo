package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * app患者信息表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:46:35
 */
@Data
public class InvalidUserDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //头像id
    private Long picId;
    //用户名（用于用户登录）
    private String userName;
//用户姓名
    private String name;
    //电话
    private String phone;
    //密码
    private String password;
    //微信ID
    private String openId;
    //融云token
    private String ryToken;
    //所在地址
    private String address;
    //身份证
    private String idCard;
    //验证码
    private String code;
    //性别
    private int sex;

    private FilesDO filesDO;
}

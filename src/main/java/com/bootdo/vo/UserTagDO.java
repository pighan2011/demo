package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 用户标签关系表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-13 21:19:42
 */
@Data
public class UserTagDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //用户id
    private Long userId;
    //标签id
    private Long tagId;
    //	标签名字
    private String tagName;

}

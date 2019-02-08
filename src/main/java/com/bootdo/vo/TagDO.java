package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 标签信息表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-13 21:18:55
 */
@Data
public class TagDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //标签名称
    private String tagName;
    //状态
    private Integer status;

}

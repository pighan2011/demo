package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-13 11:19:00
 */
@Data
public class AreaDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Integer id;
    //地区
    private String name;
    //父级ID
    private Integer pid;

}

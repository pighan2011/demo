package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 评价详情列表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:33:10
 */
@Data
public class UeDetailsDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //原名称- ------修改为问题的id
    private String name;
    //评价
    private String udDesc;
    //评价id
    private Long ueId;
}

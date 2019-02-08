package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 组织机构类型表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-13 15:55:59
 */
@Data
public class DeptTypeDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //类型名称
    private String dtName;
    //类型排序
    private Integer dtSort;
    //类型说明
    private String dtDesc;
    //类型备注
    private String dtRemarks;

}

package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 床位信息
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-12 10:33:11
 */
@Data
public class BedInfoDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //科室id
    private Long deptId;
    //科室名称
    private String deptName;
    //开放病床
    private Integer openBed;
    //剩余床位
    private Integer surplusBed;

    private Long hospitalId;

}
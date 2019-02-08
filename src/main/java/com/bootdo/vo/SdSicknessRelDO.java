package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 穷举症状详情对应疾病表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:30
 */
@Data
public class SdSicknessRelDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //症状合集
    private String collections;
    //对应病症
    private String sickness;

    private String JsonData;
}

package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 化验报告关系表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:33:33
 */
@Data
public class UserAssayRelDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //患者id
    private Long userId;
    //就诊卡id
    private Long dianpsisId;
    //图片id
    private Long picId;
    //类别
    private Integer uarClass;


}

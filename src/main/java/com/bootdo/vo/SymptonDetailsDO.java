package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 症状描述表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:33:02
 */
@Data
public class SymptonDetailsDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //症状id
    private Long sId;
    //症状名称
    private String sName;
    //详情名称
    private String sdDesc;

    private int tid;


}

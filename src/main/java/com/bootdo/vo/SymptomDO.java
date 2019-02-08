package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 症状表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:53
 */
@Data
public class SymptomDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //部位id
    private Long pobId;
    //部位名称
    private String pobName;
    //症状
    private String symptonDesc;

    private String jsonData;

}

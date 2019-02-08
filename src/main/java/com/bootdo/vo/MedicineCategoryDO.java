package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 药品类别
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-14 13:11:21
 */
@Data
public class MedicineCategoryDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //分类名称
    private String mcName;

}

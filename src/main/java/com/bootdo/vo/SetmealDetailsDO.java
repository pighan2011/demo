package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 套餐详细项目
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:46
 */
@Data
public class SetmealDetailsDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //项目名称
    private String name;
    //项目描述
    private String sdDesc;
    //套餐id
    private Long sId;

    private int tid;

}

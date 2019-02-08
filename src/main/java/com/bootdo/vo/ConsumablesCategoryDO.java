package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 消耗品类别
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-14 13:11:18
 */
@Data
public class ConsumablesCategoryDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //类别名称
    private String ccName;

}

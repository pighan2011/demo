package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 消耗品表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-14 13:11:14
 */
@Data
public class ConsumablesDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //医院id
    private Long deptId;
    //医院名称
    private String deptName;
    //类别id
    private Long ccId;
    //类别名称
    private String ccName;
    //消耗品名称
    private String name;
    //规格
    private String specifications;
    //材质
    private String material;
    //大单位
    private String bigUnit;
    //小单位
    private String smallUnit;
    //保质期
    private String selfLife;
    //使用方法
    private String way;
    //价格
    private BigDecimal price;
    //备注
    private String remarks;

}

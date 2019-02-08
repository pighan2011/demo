package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 检查项配置
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-23 15:12:15
 */
@Data
public class CheckListDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //检测项目
    private String name;
    //标本采集
    private String specimen;
    //校验时间
    private String checkTime;
    //参考范围
    private String scope;
    //临床意义
    private String significance;
    //化验检查分类
    private Integer chekcClass;

    private BigDecimal price;

}

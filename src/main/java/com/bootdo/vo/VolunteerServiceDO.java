package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 上门服务
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-12 10:32:39
 */
@Data
public class VolunteerServiceDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //类别
    private Integer vsClass;
    //名称
    private String vsName;
    //小标题
    private String title;
    //图标
    private String iconFile;
    //价格
    private BigDecimal price;
    //单位
    private String unit;
    //说明
    private String vsDesc;
    //注意事项
    private String notice;
    //我们的优势
    private String advantage;
    //退款或修改
    private String refund;
    //首页图
    private String homePic;

    private BigDecimal oldPrice;

    private long deptId;

}

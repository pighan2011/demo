package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 健康体检-套餐表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:40
 */
@Data
public class SetmealDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //首页图片
    private String homePage;
    //套餐名称
    private String name;
    //性别限制
    private String sex;
    //注意事项
    private String notice;
    //描述
    private String sDesc;
    //原价
    private BigDecimal oldPrice;
    //现价
    private BigDecimal newPrice;
    //套餐详情
    private List<SetmealDetailsDO> list;
    //创建时间
    private Date createDate;
    //状态
    private int status;
    //医院id
    private long deptId;
    //    就诊卡姓名
    private Long diagnosisName;

    private String JsonData;

    private FilesDO filesDO;

}

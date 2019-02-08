package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 基础药品表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-14 11:17:36
 */
@Data
public class MedicineDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //医院id
    private Long deptId;
    //医院名称
    private String deptName;
    //药品名称
    private String drugName;
    //成分
    private String ingredients;
    //适应症
    private String indications;
    //用法用量
    private String dosage;
    //不良反应
    private String adverseReactions;
    //禁忌
    private String taboo;
    //注意事项
    private String precautions;
    //特殊人群用药
    private String specialPopulationMedication;
    //药物互相作用
    private String medicineInteractions;
    //药理作用
    private String pharmacologicalAction;
    //贮藏
    private String storage;
    //有效期
    private String validityPeriod;
    //批准文号
    private String approvalNumber;
    //说明书修订日期
    private Date manuscriptRevisionDate;
    //生产企业
    private String manufacturer;
    //创建者
    private String createBy;
    //创建时间
    private Date createDate;
    //  价格
    private BigDecimal drugPrice;
    //	类别id
    private Long mcId;
    //	类别名称
    private String mcName;

}

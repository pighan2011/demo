package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 用户评价表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:33:40
 */
@Data
public class UserEvaluationDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //用户id
    private Long userId;
    //就诊卡id
    private Long diagnosisId;
    //建议留言
    private String message;
    //评价日期
    private Date createDate;
    //    医院id
    private Long deptId;

    private List<UeDetailsDO> list;
}

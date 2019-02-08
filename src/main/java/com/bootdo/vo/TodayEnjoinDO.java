package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 今日医嘱
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-21 14:33:29
 */
@Data
public class TodayEnjoinDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //就诊卡id
    private Long diagnosisId;
    //医生id
    private Long userId;
    //医嘱内容
    private String context;
    //发布日期
    private String createDate;

    private UserDO userDO;


}

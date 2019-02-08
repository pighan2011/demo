package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 满意度项配置
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:23
 */
@Data
public class QuestionDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //问题描述
    private String qDesc;
    //状态
    private Integer status;
//    赞
    private int up;
//踩
    private int down;

}

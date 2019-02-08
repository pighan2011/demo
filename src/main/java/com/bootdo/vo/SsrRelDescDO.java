package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 穷举疾病详细关系说明表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-26 21:04:31
 */
@Data
public class SsrRelDescDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //绑定父id
    private Long ssrId;
    //症状划分id
    private Long sdId;

    private String sdDesc;

    private int tid;

}

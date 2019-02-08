package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 图片与就诊人关系表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-03 10:56:38
 */
@Data
public class PicDianosisRelDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //图片id
    private Long picId;
    //就诊卡id
    private Long dianosisId;
    //类别
    private Integer picClass;
    //    文件信息
    private FilesDO filesDO;
    //医院名称
    private String hosName;
}

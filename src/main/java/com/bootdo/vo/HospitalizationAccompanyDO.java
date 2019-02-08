package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 陪诊人
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-23 10:34:18
 */
@Data
public class HospitalizationAccompanyDO implements Serializable {
    private static final long serialVersionUID = 1L;

            //id
        private Long id;
            //姓名
        private String name;
            //性别
        private Integer sex;
            //电话
        private String phone;
            //住院申请id
        private Long hosId;
//        陪诊天数
        private Double days;

        private int age;

    }

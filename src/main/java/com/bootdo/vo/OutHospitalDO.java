package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 出院
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2019-01-16 11:25:03
 */
@Data
public class OutHospitalDO implements Serializable {
    private static final long serialVersionUID = 1L;

            //id
        private Long id;
            //就诊卡id
        private Long diagnosisId;
            //科室id
        private Long deptId;
            //科室名称
        private String deptName;
            //房间号
        private String roomNo;
            //床位
        private String bedNo;
            //入院时间
        private String inDate;
            //出院时间
        private String outDate;
            //出院理由
        private String cause;
            //状态
        private Integer status;

    }

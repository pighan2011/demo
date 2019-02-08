package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 用车申请
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-25 10:34:09
 */
@Data
public class CarApplyDO implements Serializable {
    private static final long serialVersionUID = 1L;

            //id
        private Long id;
            //用户id
        private Long userId;
            //医院i
        private Long deptId;
            //申请人姓名
        private String applyName;
            //电话
        private String phone;
            //用车时间
        private String time;
            //用车地点
        private String address;
            //用车原因
        private String cause;

    }

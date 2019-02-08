package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 部位表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:32:08
 */
@Data
public class PartOfBodyDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //关联科室id
    private Long deptId;
    //关联科室名称
    private String deptName;
    //部位
    private String part;

    private List<SymptomDO> list;


}

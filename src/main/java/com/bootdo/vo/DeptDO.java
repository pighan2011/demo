package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 部门管理
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:28:36
 */
@Data
public class DeptDO implements Serializable {
    private static final long serialVersionUID = 1L;
    //
    private Long deptId;
    //上级部门ID，一级部门为0
    private Long parentId;
    //部门名称
    private String name;
    //排序
    private Integer orderNum;
    //是否删除  -1：已删除  0：正常
    private Integer delFlag;
    //部门类型
    private Long deptType;
    //类型说明
    private String typeName;
    //logo
    private String logo;
    //简介
    private String introduce;
    //电话
    private String phone;
    //乘车方式
    private String way;
    //网站
    private String webSite;
    //是否支持医保卡
    private Integer medicare;
    //省id
    private Integer provinceId;
    //省名称
    private String provinceName;
    //市id
    private Integer cityId;
    //区id
    private Integer areaId;
    //市名称
    private String cityName;
    //区名称
    private String areaName;
    //经纬度
    private String mapCoordinates;
    //详细地址
    private String detailAddress;
    //    子部门
    private List<DeptDO> sonDeptList;
    //距离
    private double distance;
    //经度
    private double lon;
    //纬度
    private double lat;

    private FilesDO filesDO;

}

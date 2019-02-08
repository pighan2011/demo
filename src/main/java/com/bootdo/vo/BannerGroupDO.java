package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 主页轮播图组
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-14 16:39:12
 */
@Data
public class BannerGroupDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //机构id
    private Long deptId;
    //机构名称
    private String deptName;
    //图组名
    private String groupName;
    //状态
    private Integer status;

    private List<BannerDetailsDO> list;

    private String sorts;

    private String fileId;

    private String flag;

    private String relId;


}

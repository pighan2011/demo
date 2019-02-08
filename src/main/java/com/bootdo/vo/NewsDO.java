package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 医院咨询
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-22 17:31:54
 */
@Data
public class NewsDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //标题
    private String title;
    //首页图
    private Long homePage;
    //梗概
    private String summarize;
    //详情
    private String newsDetails;
    //创建时间
    private Date createDate;
    //创建人
    private String createBy;

    private FilesDO filesDO;
    //医院id
    private Long deptId;


}

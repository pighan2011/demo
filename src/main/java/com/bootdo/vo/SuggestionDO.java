package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 意见反馈
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-12-18 16:25:25
 */
@Data
public class SuggestionDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //用户id
    private Long userId;
    //内容
    private String content;
    //创建日期
    private Date createDate;

}

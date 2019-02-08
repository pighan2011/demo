package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * 轮播图详情
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-14 16:39:10
 */
@Data
public class BannerDetailsDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //组id
    private Long groupId;
    //图片id
    private Long picId;
    //排序
    private Integer sort;

    private FilesDO filesDO;

    private String filePath;

}

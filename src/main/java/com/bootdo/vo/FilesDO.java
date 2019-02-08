package com.bootdo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 文件表
 *
 * @author Lambda~
 * @email hanzhiqiang803@gmail.com
 * @date 2018-11-13 15:55:24
 */
@Data
public class FilesDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //id
    private Long id;
    //文件名称
    private String fileName;
    //文件路径
    private String filePath;
    //文件大小
    private String fileSize;
    //文件类型
    private String fileType;
    //上传人
    private String uploadBy;
    //上传时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uploadDate;
    //文件备注
    private String fileRemarks;

    private String width;

    private String height;


}

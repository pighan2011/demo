/**
  * Copyright 2019 bejson.com 
  */
package com.bootdo.easemob.entity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Auto-generated: 2019-01-08 10:29:46
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class EasemobResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String action;
    private String application;
    private String path;
    private String uri;
    private List<Entities> entities;
    private long timestamp;
    private int duration;
    private String organization;
    private String applicationName;
    private String error;
    private String exception;
    private String error_description;
    private Map<String,String> data;

}
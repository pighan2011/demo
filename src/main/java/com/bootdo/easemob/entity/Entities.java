/**
  * Copyright 2019 bejson.com 
  */
package com.bootdo.easemob.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Auto-generated: 2019-01-08 10:29:46
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Entities implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private String type;
    private long created;
    private long modified;
    private String username;
    private boolean activated;
    private String nickname;
    private String share_secret;


}
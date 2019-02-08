package com.bootdo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResponseDO implements Serializable {

    // http 状态码
    private int code;

    // 返回信息
    private String msg;

    // 返回的数据
    private Object data;

    //返回时间
    private Date responseTime;

    public ResponseDO(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.responseTime = new Date();
    }

}

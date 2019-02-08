package com.bootdo.easemob.entity;

import io.swagger.client.model.Msg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileMsg extends Msg {

    private MultipartFile multipartFile;

    private Map<String, String> size;

    private int leangth;

}

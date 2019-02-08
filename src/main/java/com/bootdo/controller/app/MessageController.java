package com.bootdo.controller.app;


import com.bootdo.easemob.entity.EasemobResponse;
import com.bootdo.easemob.entity.FileMsg;
import com.bootdo.util.EasemobUtil;
import com.bootdo.vo.ResponseDO;
import io.swagger.client.model.Msg;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/msg")
public class MessageController {


    @PostMapping("/sendMsg")
    public ResponseDO sendMsg(Msg msg) {
        EasemobResponse easemobResponse = EasemobUtil.sendMsg(msg);
        if (EasemobUtil.sendMsgFlag(easemobResponse))
            return new ResponseDO(200, "发送成功", null);
        return new ResponseDO(201, "发送失败", easemobResponse);
    }

    @PostMapping("/sendPicOrVoiceMsg/{type}")
    public ResponseDO sendPicOrVoiceMsg(@PathVariable Integer type, FileMsg fileMsg) throws IOException {
        BufferedImage image = ImageIO.read(fileMsg.getMultipartFile().getInputStream());
        File file1 = new File(Objects.requireNonNull(fileMsg.getMultipartFile().getOriginalFilename()));
        FileUtils.copyInputStreamToFile(fileMsg.getMultipartFile().getInputStream(), file1);
        EasemobResponse easemobResponse = EasemobUtil.uploadFile(file1);
        Map<String, String> msg = new HashMap<>();
        if (type == 1) {
//            发送图片
            Map<String, String> size = new HashMap<>();
            size.put("width", String.valueOf(image.getWidth()));
            size.put("height", String.valueOf(image.getHeight()));
            msg.put("type", "img");
            msg.put("filename", fileMsg.getMultipartFile().getName());
            msg.put("secret", easemobResponse.getEntities().get(0).getShare_secret());
            msg.put("url", easemobResponse.getEntities().get(0).getUuid());
            fileMsg.setMsg(msg);
            fileMsg.setSize(size);
        } else {
            msg.put("type", "audio");
            msg.put("length", String.valueOf(fileMsg.getLeangth()));
            msg.put("secret", easemobResponse.getEntities().get(0).getShare_secret());
            msg.put("url", easemobResponse.getEntities().get(0).getUuid());
        }
        EasemobResponse response = EasemobUtil.sendMsg(fileMsg);
        if (EasemobUtil.sendMsgFlag(response))
            return new ResponseDO(200, "发送成功", null);
        return new ResponseDO(201, "发送失败", response);
    }

}

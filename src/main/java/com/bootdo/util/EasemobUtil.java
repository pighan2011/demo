package com.bootdo.util;

import com.alibaba.fastjson.JSON;
import com.bootdo.easemob.api.impl.EasemobFile;
import com.bootdo.easemob.api.impl.EasemobIMUsers;
import com.bootdo.easemob.api.impl.EasemobSendMessage;
import com.bootdo.easemob.entity.EasemobResponse;
import io.swagger.client.model.Msg;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.User;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EasemobUtil {

    private final static EasemobIMUsers imUsers = new EasemobIMUsers();
    private final static EasemobSendMessage sendMessage = new EasemobSendMessage();
    private final static EasemobFile fileAPI = new EasemobFile();

    //    注册用户
    public static EasemobResponse RegisterUser(String name, String pwd) {
        RegisterUsers registerUsers = new RegisterUsers();
        User user = new User().username(name).password(pwd);
        registerUsers.add(user);
        String json = dealJson(imUsers.createNewIMUserSingle(registerUsers).toString());
        return JSON.parseObject(json, EasemobResponse.class);
    }

    //    注册用户
    public static EasemobResponse sendMsg(Msg msg) {
        String json = dealJson(sendMessage.sendMessage(msg).toString());
        return JSON.parseObject(json, EasemobResponse.class);
    }

    //上传文件
    public static EasemobResponse uploadFile(File file) {
        String json = dealJson(fileAPI.uploadFile(file).toString());
        json = json.replace("share-secret", "share_secret");
        return JSON.parseObject(json, EasemobResponse.class);
    }

    //    处理json
    private static String dealJson(String json) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(json);
        return m.replaceAll("");
    }

    public static boolean sendMsgFlag(@NotNull EasemobResponse easemobResponse) {
        AtomicBoolean flag = new AtomicBoolean(true);
        easemobResponse.getData().keySet().stream().filter(
                key -> !easemobResponse.getData()
                        .get(key).equals("success")).map(key -> false)
                .forEach(flag::set);
        return flag.get();
    }
}

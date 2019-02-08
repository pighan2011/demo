package com.bootdo.easemob.api.impl;


import com.bootdo.easemob.api.SendMessageAPI;
import com.bootdo.easemob.comm.EasemobAPI;
import com.bootdo.easemob.comm.OrgInfo;
import com.bootdo.easemob.comm.ResponseHandler;
import com.bootdo.easemob.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.MessagesApi;
import io.swagger.client.model.Msg;

public class EasemobSendMessage implements SendMessageAPI {
    private ResponseHandler responseHandler = new ResponseHandler();
    private MessagesApi api = new MessagesApi();
    @Override
    public Object sendMessage(final Object payload) {
        return responseHandler.handle(() -> api.orgNameAppNameMessagesPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(), (Msg) payload));
    }
}

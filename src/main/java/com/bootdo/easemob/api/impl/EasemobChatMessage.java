package com.bootdo.easemob.api.impl;


import com.bootdo.easemob.api.ChatMessageAPI;
import com.bootdo.easemob.comm.EasemobAPI;
import com.bootdo.easemob.comm.OrgInfo;
import com.bootdo.easemob.comm.ResponseHandler;
import com.bootdo.easemob.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.ChatHistoryApi;


public class EasemobChatMessage  implements ChatMessageAPI {

    private ResponseHandler responseHandler = new ResponseHandler();
    private ChatHistoryApi api = new ChatHistoryApi();

    @Override
    public Object exportChatMessages(final Long limit,final String cursor,final String query) {
        return responseHandler.handle(() -> api.orgNameAppNameChatmessagesGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, TokenUtil.getAccessToken(),query,limit+"",cursor));
    }

    @Override
    public Object exportChatMessages(final String timeStr) {
        return responseHandler.handle(() -> api.orgNameAppNameChatmessagesTimeGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),timeStr));
    }
}

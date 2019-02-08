package com.bootdo.easemob.api.impl;


import com.bootdo.easemob.api.IMUserAPI;
import com.bootdo.easemob.comm.EasemobAPI;
import com.bootdo.easemob.comm.OrgInfo;
import com.bootdo.easemob.comm.ResponseHandler;
import com.bootdo.easemob.comm.TokenUtil;
import io.swagger.client.ApiException;
import io.swagger.client.api.UsersApi;
import io.swagger.client.model.NewPassword;
import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.UserNames;


public class EasemobIMUsers  implements IMUserAPI {

	private UsersApi api = new UsersApi();
	private ResponseHandler responseHandler = new ResponseHandler();
	@Override
	public Object createNewIMUserSingle(final Object payload) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, (RegisterUsers) payload, TokenUtil.getAccessToken()));
	}

	@Override
	public Object createNewIMUserBatch(final Object payload) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, (RegisterUsers) payload,TokenUtil.getAccessToken()));
	}

	@Override
	public Object getIMUserByUserName(final String userName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersUsernameGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName));
	}

	@Override
	public Object getIMUsersBatch(final Long limit,final String cursor) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),limit+"",cursor));
	}

	@Override
	public Object deleteIMUserByUserName(final String userName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersUsernameDelete(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName));
	}

	@Override
	public Object deleteIMUserBatch(final Long limit,final String cursor) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersDelete(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),limit+"",cursor));
	}

	@Override
	public Object modifyIMUserPasswordWithAdminToken(final String userName, final Object payload) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersUsernamePasswordPut(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,userName, (NewPassword) payload,TokenUtil.getAccessToken()));
	}

	@Override
	public Object modifyIMUserNickNameWithAdminToken(final String userName,final Object payload) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersUsernamePut(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,userName, (Nickname) payload,TokenUtil.getAccessToken()));
	}

	@Override
	public Object addFriendSingle(final String userName,final String friendName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersOwnerUsernameContactsUsersFriendUsernamePost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName,friendName));
	}

	@Override
	public Object deleteFriendSingle(final String userName,final String friendName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersOwnerUsernameContactsUsersFriendUsernameDelete(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName,friendName));
	}

	@Override
	public Object getFriends(final String userName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersOwnerUsernameContactsUsersGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName));
	}

	@Override
	public Object getBlackList(final String userName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersOwnerUsernameBlocksUsersGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName));
	}

	@Override
	public Object addToBlackList(final String userName,final Object payload) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersOwnerUsernameBlocksUsersPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName, (UserNames) payload));
	}

	@Override
	public Object removeFromBlackList(final String userName,final String blackListName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersOwnerUsernameBlocksUsersBlockedUsernameDelete(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName,blackListName));
	}

	@Override
	public Object getIMUserStatus(final String userName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersUsernameStatusGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName));
	}

	@Override
	public Object getOfflineMsgCount(final String userName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersOwnerUsernameOfflineMsgCountGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName));
	}

	@Override
	public Object getSpecifiedOfflineMsgStatus(final String userName,final String msgId) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersUsernameOfflineMsgStatusMsgIdGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName,msgId));
	}

	@Override
	public Object deactivateIMUser(final String userName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersUsernameDeactivatePost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName));
	}

	@Override
	public Object activateIMUser(final String userName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersUsernameActivatePost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName));
	}

	@Override
	public Object disconnectIMUser(final String userName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersUsernameDisconnectGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName));
	}

	@Override
	public Object getIMUserAllChatGroups(final String userName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersUsernameJoinedChatgroupsGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName));
	}

	@Override
	public Object getIMUserAllChatRooms(final String userName) {
		return responseHandler.handle(() -> api.orgNameAppNameUsersUsernameJoinedChatroomsGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName));
	}
}

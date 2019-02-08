package com.bootdo.easemob.api.impl;


import com.bootdo.easemob.api.AuthTokenAPI;
import com.bootdo.easemob.comm.TokenUtil;

public class EasemobAuthToken implements AuthTokenAPI {

	@Override
	public Object getAuthToken(){
		return TokenUtil.getAccessToken();
	}
}

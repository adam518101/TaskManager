package com.coal.black.bc.socket.client.handlers;

import com.coal.black.bc.socket.IDtoBase;

import java.util.ArrayList;
import java.util.List;

import com.coal.black.bc.socket.client.SocketClient;
import com.coal.black.bc.socket.client.returndto.BasicResult;
import com.coal.black.bc.socket.client.returndto.LoginResult;
import com.coal.black.bc.socket.dto.LoginDto;
import com.coal.black.bc.socket.enums.OperateType;
import com.coal.black.bc.socket.utils.SecurityUtil;

public class UserLoginHandler {
	public LoginResult login(String userName, String pwd) {
		SocketClient client = new SocketClient();
		LoginDto loginDto = new LoginDto();
		loginDto.setUserCode(userName);
		try {
			pwd = SecurityUtil.encodeByMD5(pwd);
		} catch (Exception ex) {
		}
		loginDto.setMd5Pwd(pwd);
		List<IDtoBase> list = new ArrayList<IDtoBase>();
		list.add(loginDto);
		BasicResult basicResult = client.deal(OperateType.LoginIn, -1, list, this);
		if (basicResult instanceof LoginResult) {
			return (LoginResult) basicResult;
		} else {
			return new LoginResult(basicResult, -1);
		}
	}
}

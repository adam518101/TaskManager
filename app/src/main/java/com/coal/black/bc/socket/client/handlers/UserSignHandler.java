package com.coal.black.bc.socket.client.handlers;

import java.util.ArrayList;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.SocketClient;
import com.coal.black.bc.socket.client.returndto.BasicResult;
import com.coal.black.bc.socket.client.returndto.SignInResult;
import com.coal.black.bc.socket.dto.SignInDto;
import com.coal.black.bc.socket.enums.OperateType;

public class UserSignHandler {
	public SignInResult signIn(List<SignInDto> signList) {
		SocketClient client = new SocketClient();
		List<IDtoBase> list = new ArrayList<IDtoBase>();
		for (SignInDto dto : signList) {
			list.add(dto);
		}
		BasicResult basicResult = client.deal(OperateType.SingIn, ClientGlobal.getUserId(), list, this);
		if (basicResult instanceof SignInResult) {
			return (SignInResult) basicResult;
		} else {
			SignInResult signInResult = new SignInResult(basicResult);
			return signInResult;
		}
	}
}

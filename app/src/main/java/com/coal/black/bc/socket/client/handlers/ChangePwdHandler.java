package com.coal.black.bc.socket.client.handlers;

import java.util.ArrayList;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.SocketClient;
import com.coal.black.bc.socket.client.returndto.BasicResult;
import com.coal.black.bc.socket.client.returndto.ChangePwdResult;
import com.coal.black.bc.socket.dto.ChangePwdDto;
import com.coal.black.bc.socket.enums.OperateType;

public class ChangePwdHandler {
	public ChangePwdResult changePwd(String oldPwd, String newPwd) {
		List<IDtoBase> listDto = new ArrayList<IDtoBase>();
		ChangePwdDto cpw = new ChangePwdDto();
		cpw.setOldPwd(oldPwd);
		cpw.setNewPwd(newPwd);
		listDto.add(cpw);

		SocketClient client = new SocketClient();
		BasicResult result = client.deal(OperateType.ChangePwd, ClientGlobal.getUserId(), listDto, this);
		if (result instanceof ChangePwdResult) {
			return (ChangePwdResult) result;
		} else {
			return new ChangePwdResult(result);
		}
	}
}

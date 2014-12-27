package com.coal.black.bc.socket.client.handlers;

import java.util.ArrayList;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.SocketClient;
import com.coal.black.bc.socket.client.returndto.BasicResult;
import com.coal.black.bc.socket.client.returndto.UserTaskStatusChangeResult;
import com.coal.black.bc.socket.dto.UserTaskStatusChangeDto;
import com.coal.black.bc.socket.enums.OperateType;

public class UserTaskStatusChangeHandler {
	public UserTaskStatusChangeResult changeUserTaskStatus(int taskId, int userTaskStatus) {
		SocketClient client = new SocketClient();
		List<IDtoBase> list = new ArrayList<IDtoBase>();
		UserTaskStatusChangeDto dto = new UserTaskStatusChangeDto();
		dto.setUserId(ClientGlobal.userId);
		dto.setTaskId(taskId);
		dto.setUserTaskStatus(userTaskStatus);
		list.add(dto);

		BasicResult basicResult = client.deal(OperateType.UserTaskStatusChange, ClientGlobal.userId, list, this);
		if (basicResult instanceof UserTaskStatusChangeResult) {
			return (UserTaskStatusChangeResult) basicResult;
		} else {
			return new UserTaskStatusChangeResult(basicResult);
		}
	}
}

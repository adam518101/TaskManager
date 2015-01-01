package com.coal.black.bc.socket.client.handlers;

import java.util.ArrayList;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.SocketClient;
import com.coal.black.bc.socket.client.returndto.BasicResult;
import com.coal.black.bc.socket.client.returndto.TaskQueryResult;
import com.coal.black.bc.socket.dto.TaskQueryDto;
import com.coal.black.bc.socket.enums.OperateType;

public class TaskQueryHandler {
	public TaskQueryResult qryTasks(int[] userStatusList) {
		SocketClient socketClient = new SocketClient();
		List<IDtoBase> lists = new ArrayList<IDtoBase>();
		TaskQueryDto queryDto = new TaskQueryDto();
		queryDto.setUserTaskStatus(userStatusList);
		lists.add(queryDto);
		BasicResult result = socketClient.deal(OperateType.TaskQuery, ClientGlobal.getUserId(), lists, this);
		if (result instanceof TaskQueryResult) {
			return (TaskQueryResult) result;
		} else {
			TaskQueryResult result1 = new TaskQueryResult(result);
			return result1;
		}
	}
}

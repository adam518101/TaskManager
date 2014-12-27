package com.coal.black.bc.socket.client.handlers;

import java.util.ArrayList;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.SocketClient;
import com.coal.black.bc.socket.client.returndto.BasicResult;
import com.coal.black.bc.socket.client.returndto.TaskQryUserNewTaskCountResult;
import com.coal.black.bc.socket.client.returndto.TaskQryUserNewTaskListResult;
import com.coal.black.bc.socket.dto.TaskQryUserNewTaskDto;
import com.coal.black.bc.socket.enums.OperateType;

public class TaskQryUserNewTaskHandler {
	/**
	 * 查询新的任务的个数
	 * 
	 * @param lastGrantTime
	 *            任务列表中最近一次的授权时间（所有大于这个时间的，状态为1的任务都是新任务信息）
	 * @return
	 */
	public TaskQryUserNewTaskCountResult qryNewTaskCount(Long lastGrantTime) {
		SocketClient socketClient = new SocketClient();
		List<IDtoBase> lists = new ArrayList<IDtoBase>();
		TaskQryUserNewTaskDto queryDto = new TaskQryUserNewTaskDto();
		queryDto.setLastGrantTime(lastGrantTime);
		lists.add(queryDto);
		BasicResult result = socketClient.deal(OperateType.TaskQryUserNewTaskCount, ClientGlobal.userId, lists, this);
		if (result instanceof TaskQryUserNewTaskCountResult) {
			return (TaskQryUserNewTaskCountResult) result;
		} else {
			TaskQryUserNewTaskCountResult result1 = new TaskQryUserNewTaskCountResult(result);
			return result1;
		}
	}

	/**
	 * 查询新任务列表
	 * 
	 * @param lastGrantTime
	 *            任务列表中最近一次的授权时间（所有大于这个时间的，状态为1的任务都是新任务信息）
	 * @return
	 */
	public TaskQryUserNewTaskListResult qryNewTaskList(Long lastGrantTime) {
		SocketClient socketClient = new SocketClient();
		List<IDtoBase> lists = new ArrayList<IDtoBase>();
		TaskQryUserNewTaskDto queryDto = new TaskQryUserNewTaskDto();
		queryDto.setLastGrantTime(lastGrantTime);
		lists.add(queryDto);
		BasicResult result = socketClient.deal(OperateType.TaskQryUserNewTaskList, ClientGlobal.userId, lists, this);
		if (result instanceof TaskQryUserNewTaskListResult) {
			return (TaskQryUserNewTaskListResult) result;
		} else {
			TaskQryUserNewTaskListResult result1 = new TaskQryUserNewTaskListResult(result);
			return result1;
		}
	}
}

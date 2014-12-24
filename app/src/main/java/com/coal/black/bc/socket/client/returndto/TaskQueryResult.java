package com.coal.black.bc.socket.client.returndto;

import java.util.ArrayList;
import java.util.List;

import com.coal.black.bc.socket.dto.TaskDto;

/**
 * 查询任务返回的结果列表
 * 
 * @author wanghui-bc
 *
 */
public class TaskQueryResult extends BasicResult {
	private List<TaskDto> taskList = new ArrayList<TaskDto>();

	public TaskQueryResult() {

	}

	public TaskQueryResult(BasicResult basicResult) {
		setSuccess(basicResult.isSuccess());
		setBusException(basicResult.isBusException());
		setBusinessErrorCode(basicResult.getBusinessErrorCode());
		setThrowable(basicResult.getThrowable());
		this.taskList = null;
	}

	public List<TaskDto> getTaskList() {
		return taskList;
	}

	public void addTask(TaskDto task) {
		this.taskList.add(task);
	}

	public void clearTaskList() {
		this.taskList.clear();
	}
}

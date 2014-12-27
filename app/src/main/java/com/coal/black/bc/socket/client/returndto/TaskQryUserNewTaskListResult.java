package com.coal.black.bc.socket.client.returndto;

import java.util.ArrayList;
import java.util.List;

import com.coal.black.bc.socket.dto.TaskDto;

public class TaskQryUserNewTaskListResult extends BasicResult {
	private List<TaskDto> taskList = new ArrayList<TaskDto>();

	public TaskQryUserNewTaskListResult() {

	}

	public TaskQryUserNewTaskListResult(BasicResult basicResult) {
		setSuccess(basicResult.isSuccess());
		setBusException(basicResult.isBusException());
		setBusinessErrorCode(basicResult.getBusinessErrorCode());
		setThrowable(basicResult.getThrowable());
		this.taskList.clear();
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

package com.coal.black.bc.socket.client.returndto;

import com.coal.black.bc.socket.dto.TaskDto;

public class TaskQueryByTaskIDResult extends BasicResult {
	private TaskDto taskDto = null;

	public TaskQueryByTaskIDResult() {

	}

	public TaskQueryByTaskIDResult(BasicResult basicResult) {
		setSuccess(basicResult.isSuccess());
		setBusException(basicResult.isBusException());
		setBusinessErrorCode(basicResult.getBusinessErrorCode());
		setThrowable(basicResult.getThrowable());
		this.taskDto = null;
	}

	public TaskDto getTaskDto() {
		return taskDto;
	}

	public void setTaskDto(TaskDto taskDto) {
		this.taskDto = taskDto;
	}
}

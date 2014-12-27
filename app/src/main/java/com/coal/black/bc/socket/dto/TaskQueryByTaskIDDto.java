package com.coal.black.bc.socket.dto;

import com.coal.black.bc.socket.IDtoBase;

public class TaskQueryByTaskIDDto extends IDtoBase {
	private static final long serialVersionUID = -6831321620186248106L;

	private int taskId;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

}

package com.coal.black.bc.socket.dto;

import com.coal.black.bc.socket.IDtoBase;

public class TaskQryUserNewTaskDto extends IDtoBase {
	private static final long serialVersionUID = -287617404409661604L;
	private long lastGrantTime;

	public long getLastGrantTime() {
		return lastGrantTime;
	}

	public void setLastGrantTime(long lastGrantTime) {
		this.lastGrantTime = lastGrantTime;
	}

}

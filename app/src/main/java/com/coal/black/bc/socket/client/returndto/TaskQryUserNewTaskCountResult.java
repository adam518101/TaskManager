package com.coal.black.bc.socket.client.returndto;

public class TaskQryUserNewTaskCountResult extends BasicResult {
	private int count;

	public TaskQryUserNewTaskCountResult() {

	}

	public TaskQryUserNewTaskCountResult(BasicResult basicResult) {
		setSuccess(basicResult.isSuccess());
		setBusException(basicResult.isBusException());
		setBusinessErrorCode(basicResult.getBusinessErrorCode());
		setThrowable(basicResult.getThrowable());
		count = 0;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}

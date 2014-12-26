package com.coal.black.bc.socket.client.returndto;

public class UserTaskStatusChangeResult extends BasicResult {
	public UserTaskStatusChangeResult() {

	}

	public UserTaskStatusChangeResult(BasicResult basicResult) {
		setSuccess(basicResult.isSuccess());
		setBusException(basicResult.isBusException());
		setBusinessErrorCode(basicResult.getBusinessErrorCode());
		setThrowable(basicResult.getThrowable());
	}
}

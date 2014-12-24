package com.coal.black.bc.socket.client.returndto;

public class LoginResult extends BasicResult {
	public LoginResult(BasicResult basicResult, int userId) {
		setSuccess(basicResult.isSuccess());
		setBusException(basicResult.isBusException());
		setBusinessErrorCode(basicResult.getBusinessErrorCode());
		setThrowable(basicResult.getThrowable());
		this.userId = userId;
	}

	public LoginResult() {

	}

	private int userId = -1;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}

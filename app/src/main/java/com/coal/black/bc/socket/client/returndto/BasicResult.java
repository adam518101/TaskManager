package com.coal.black.bc.socket.client.returndto;

public class BasicResult {
	private boolean success = false;
	private boolean isBusException = false;
	private byte businessErrorCode = -1;
	private Throwable throwable;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isBusException() {
		return isBusException;
	}

	public void setBusException(boolean isBusException) {
		this.isBusException = isBusException;
	}

	public byte getBusinessErrorCode() {
		return businessErrorCode;
	}

	public void setBusinessErrorCode(byte businessErrorCode) {
		this.businessErrorCode = businessErrorCode;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}

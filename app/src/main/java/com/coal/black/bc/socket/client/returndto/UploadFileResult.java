package com.coal.black.bc.socket.client.returndto;

public class UploadFileResult extends BasicResult {
	public UploadFileResult() {

	}

	public UploadFileResult(BasicResult basicResult) {
		setSuccess(basicResult.isSuccess());
		setBusException(basicResult.isBusException());
		setBusinessErrorCode(basicResult.getBusinessErrorCode());
		setThrowable(basicResult.getThrowable());
		this.uploadSuccess = false;
	}

	private boolean uploadSuccess;

	public boolean isUploadSuccess() {
		return uploadSuccess;
	}

	public void setUploadSuccess(boolean uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}
}

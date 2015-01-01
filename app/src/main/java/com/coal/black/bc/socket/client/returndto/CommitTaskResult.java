package com.coal.black.bc.socket.client.returndto;

/**
 * 提交任务的返回结果
 * 
 * @author wanghui-bc
 *
 */
public class CommitTaskResult extends BasicResult {
	public CommitTaskResult() {

	}

	public CommitTaskResult(BasicResult basicResult) {
		setSuccess(basicResult.isSuccess());
		setBusException(basicResult.isBusException());
		setBusinessErrorCode(basicResult.getBusinessErrorCode());
		setThrowable(basicResult.getThrowable());
	}
}

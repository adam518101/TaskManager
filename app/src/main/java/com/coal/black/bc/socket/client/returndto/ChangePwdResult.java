package com.coal.black.bc.socket.client.returndto;

/**
 * 修改密码的返回结果
 * 
 * @author wanghui-bc
 *
 */
public class ChangePwdResult extends BasicResult {
	public ChangePwdResult() {

	}

	public ChangePwdResult(BasicResult basicResult) {
		setSuccess(basicResult.isSuccess());
		setBusException(basicResult.isBusException());
		setBusinessErrorCode(basicResult.getBusinessErrorCode());
		setThrowable(basicResult.getThrowable());
	}
}

package com.coal.black.bc.socket.client.returndto;

import java.util.ArrayList;
import java.util.List;

public class SignInResult extends BasicResult {
	public SignInResult() {

	}

	public SignInResult(BasicResult basicResult) {
		setSuccess(basicResult.isSuccess());
		setBusException(basicResult.isBusException());
		setBusinessErrorCode(basicResult.getBusinessErrorCode());
		setThrowable(basicResult.getThrowable());
	}

	private List<Boolean> resultList = new ArrayList<Boolean>();

	public List<Boolean> getResultList() {
		return resultList;
	}

	public void addResult(Boolean result) {
		this.resultList.add(result);
	}

	public void clearResultList() {
		this.resultList.clear();
	}
}

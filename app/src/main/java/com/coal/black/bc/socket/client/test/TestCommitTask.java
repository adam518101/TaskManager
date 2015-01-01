package com.coal.black.bc.socket.client.test;

import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.handlers.CommitTaskHandler;
import com.coal.black.bc.socket.client.returndto.CommitTaskResult;

public class TestCommitTask {
	public static void main(String[] args) {
		ClientGlobal.setUserId(11);
		ClientGlobal.setMacAddress("7C:E9:D3:EF:FA:10");
		CommitTaskHandler handler = new CommitTaskHandler();
		CommitTaskResult result = handler.commitTask(1, true, true, "提交报告，提交报告");
		if (result.isSuccess()) {
			System.out.println("Success");
		} else {
			if (result.isBusException()) {
				System.out.println("Business Exception, exception code is " + result.getBusinessErrorCode());
			} else {
				System.out.println("Other Exception, exception type is " + result.getThrowable());
			}
		}
	}
}

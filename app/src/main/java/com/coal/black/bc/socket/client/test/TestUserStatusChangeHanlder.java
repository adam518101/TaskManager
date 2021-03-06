package com.coal.black.bc.socket.client.test;

import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.handlers.UserTaskStatusChangeHandler;
import com.coal.black.bc.socket.client.returndto.UserTaskStatusChangeResult;
import com.coal.black.bc.socket.common.UserTaskStatusCommon;

public class TestUserStatusChangeHanlder {
	public static void main(String[] args) {
		ClientGlobal.setUserId(11);

		UserTaskStatusChangeHandler handler = new UserTaskStatusChangeHandler();
		UserTaskStatusChangeResult result = handler.changeUserTaskStatus(1, UserTaskStatusCommon.IN_DEALING);
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

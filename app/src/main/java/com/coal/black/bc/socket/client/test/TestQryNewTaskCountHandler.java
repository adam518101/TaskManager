package com.coal.black.bc.socket.client.test;

import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.handlers.TaskQryUserNewTaskHandler;
import com.coal.black.bc.socket.client.returndto.TaskQryUserNewTaskCountResult;

public class TestQryNewTaskCountHandler {
	public static void main(String[] args) {
		ClientGlobal.userId = 11;
		TaskQryUserNewTaskHandler handler = new TaskQryUserNewTaskHandler();
        long time = 1419923417454l;
        System.out.println(time);
        TaskQryUserNewTaskCountResult countResult = handler.qryNewTaskCount(time);
		if (countResult.isSuccess()) {
			System.out.println("Success, result count is " + countResult.getCount());
		} else {
			if (countResult.isBusException()) {
				System.out.println("Business Exception, exception code is " + countResult.getBusinessErrorCode());
			} else {
				System.out.println("Other Exception, exception type is " + countResult.getThrowable());
			}
		}
	}
}

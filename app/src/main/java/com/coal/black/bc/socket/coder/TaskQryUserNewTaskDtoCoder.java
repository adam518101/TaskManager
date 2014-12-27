package com.coal.black.bc.socket.coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.coal.black.bc.socket.Constants;
import com.coal.black.bc.socket.dto.TaskQryUserNewTaskDto;
import com.coal.black.bc.socket.exception.BusinessException;

public class TaskQryUserNewTaskDtoCoder {
	public static byte[] toWire(TaskQryUserNewTaskDto qryUserNewTaskDto) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		try {
			dout.writeLong(qryUserNewTaskDto.getLastGrantTime());
		} catch (IOException ex) {
			throw new BusinessException(Constants.TASK_QUERY_USER_NEW_TASK_CODER_TO_WIRE_ERROR, ex);
		}
		return out.toByteArray();
	}

	public static TaskQryUserNewTaskDto fromWire(byte[] bytes) {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(in);
		TaskQryUserNewTaskDto uts = new TaskQryUserNewTaskDto();
		try {
			long lastOperateTime = din.readLong();
			uts.setLastGrantTime(lastOperateTime);
		} catch (IOException ex) {
			throw new BusinessException(Constants.TASK_QUERY_USER_NEW_TASK_CODER_FORM_WIRE_ERROR, ex);
		}
		return uts;
	}
}

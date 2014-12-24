package com.coal.black.bc.socket.coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import com.coal.black.bc.socket.Constants;
import com.coal.black.bc.socket.dto.TaskQueryDto;
import com.coal.black.bc.socket.exception.BusinessException;

public class TaskQueryDtoCoder {
	public static byte[] toWire(TaskQueryDto taskQueryDto) {
		int[] userTaskStatus = taskQueryDto.getUserTaskStatus();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		try {
			dout.writeInt(userTaskStatus.length);
			for (int i : userTaskStatus) {
				dout.writeInt(i);
			}
		} catch (IOException ex) {
			throw new BusinessException(Constants.TASK_QUERY_CODER_TO_WIRE_ERROR, ex);
		}
		return out.toByteArray();
	}

	public static TaskQueryDto fromWire(byte[] bytes) {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(in);
		TaskQueryDto queryDto = new TaskQueryDto();
		try {
			int length = din.readInt();
			int[] userStatusList = new int[length];
			for (int i = 0; i < userStatusList.length; i++) {
				userStatusList[i] = din.readInt();
			}
			queryDto.setUserTaskStatus(userStatusList);
		} catch (IOException ex) {
			throw new BusinessException(Constants.TASK_QUERY_CODER_FORM_WIRE_ERROR, ex);
		}
		return queryDto;
	}
}

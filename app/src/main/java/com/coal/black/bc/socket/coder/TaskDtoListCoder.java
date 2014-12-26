package com.coal.black.bc.socket.coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.coal.black.bc.socket.Constants;
import com.coal.black.bc.socket.dto.TaskDto;
import com.coal.black.bc.socket.exception.BusinessException;

/**
 * TaskDto的List进行Coder和Decoder
 * 
 * @author wanghui-bc
 *
 */
public class TaskDtoListCoder {
	public static byte[] toWire(List<TaskDto> taskList) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		try {
			int length = taskList.size();
			dout.writeInt(length);
			for (TaskDto taskDto : taskList) {
				byte[] bytes = TaskDtoCoder.toWire(taskDto);
				dout.writeInt(bytes.length);
				dout.write(bytes);
			}
		} catch (IOException ex) {
			throw new BusinessException(Constants.TASK_LIST_CODER_TO_WIRE_ERROR, ex);
		}
		return out.toByteArray();
	}

	/**
	 * 从Task对应的bytes中获取TaskList信息
	 * 
	 * @param bytes
	 * @return
	 */
	public static List<TaskDto> fromWire(byte[] bytes) {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(in);
		List<TaskDto> taskDtoList = new ArrayList<TaskDto>();
		try {
			int size = din.readInt();
			if (size > 0) {// 只有在task有效的情况下
				for (int i = 0; i < size; i++) {// 只做两次
					int length = din.readInt();// 获取length信息
					byte[] dtoBytes = new byte[length];
					int readLength = din.read(dtoBytes, 0, length);
					if (readLength == -1) {
						break;
					}
					if (readLength != length) {
						throw new BusinessException(Constants.TASK_LIST_CODER_FORM_WIRE_ERROR, new RuntimeException(
								"The read length is not same as the real length"));
					}
					TaskDto taskDto = TaskDtoCoder.fromWire(dtoBytes);
					taskDtoList.add(taskDto);
				}
			}
			if (taskDtoList.size() != size) {
				throw new BusinessException(Constants.TASK_LIST_CODER_FORM_WIRE_ERROR, new RuntimeException(
						"The read task list size is not same as the real length"));
			}
		} catch (IOException ex) {
			throw new BusinessException(Constants.TASK_LIST_CODER_FORM_WIRE_ERROR, ex);
		}
		return taskDtoList;
	}
}

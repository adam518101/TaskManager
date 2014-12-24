package com.coal.black.bc.socket.client.deal;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.returndto.TaskQueryResult;
import com.coal.black.bc.socket.coder.ClientInfoCoder;
import com.coal.black.bc.socket.coder.ServerReturnFlagCoder;
import com.coal.black.bc.socket.coder.TaskDtoListCoder;
import com.coal.black.bc.socket.coder.TaskQueryDtoCoder;
import com.coal.black.bc.socket.dto.ClientInfoDto;
import com.coal.black.bc.socket.dto.ServerReturnFlagDto;
import com.coal.black.bc.socket.dto.TaskDto;
import com.coal.black.bc.socket.dto.TaskQueryDto;

public class TaskQueryDeal {
	public TaskQueryResult deal(ClientInfoDto clientDto, List<IDtoBase> dtoList, InputStream in, OutputStream out) {
		try {
			TaskQueryDto queryDto = (TaskQueryDto) dtoList.get(0);
			byte[] queryTaskBytes = TaskQueryDtoCoder.toWire(queryDto);// 转换为QueryDto信息
			clientDto.setDataLength(queryTaskBytes.length);

			byte[] clientBytes = ClientInfoCoder.toWire(clientDto);
			out.write(clientBytes);// 向客户端写入clientBytes
			out.write(queryTaskBytes);// 向客户端写入查询的条件
			out.flush();

			byte[] serverFlageBytes = new byte[ServerReturnFlagDto.bytesLength];
			in.read(serverFlageBytes, 0, ServerReturnFlagDto.bytesLength);
			ServerReturnFlagDto returnFlag = ServerReturnFlagCoder.fromWire(serverFlageBytes);
			TaskQueryResult result = new TaskQueryResult();
			if (returnFlag.isSuccess()) {
				int dataLength = returnFlag.getDataLength();// 获取数据的长度
				byte data[] = new byte[dataLength];
				in.read(data, 0, dataLength);
				List<TaskDto> taskDtoList = TaskDtoListCoder.fromWire(data);
				for (TaskDto task : taskDtoList) {
					result.addTask(task);
				}
				result.setBusException(false);
				result.setSuccess(true);
				result.setBusinessErrorCode((byte) -1);
				result.setThrowable(null);
				return result;
			} else {
				result.setSuccess(false);
				result.setBusException(returnFlag.isBusinessException());
				result.setBusinessErrorCode(returnFlag.getExceptionCode());
				result.setThrowable(null);
				result.clearTaskList();
			}
			return result;
		} catch (Throwable ex) {
			TaskQueryResult result = new TaskQueryResult();
			result.setSuccess(false);
			result.setBusException(false);
			result.setBusinessErrorCode((byte) -1);
			result.setThrowable(ex);
			result.clearTaskList();
			return result;
		}
	}
}

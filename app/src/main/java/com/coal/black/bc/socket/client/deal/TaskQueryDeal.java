package com.coal.black.bc.socket.client.deal;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.returndto.TaskQueryResult;
import com.coal.black.bc.socket.coder.ClientInfoDtoCoder;
import com.coal.black.bc.socket.coder.ServerReturnFlagCoder;
import com.coal.black.bc.socket.coder.TaskDtoListCoder;
import com.coal.black.bc.socket.coder.TaskQueryDtoCoder;
import com.coal.black.bc.socket.dto.ClientInfoDto;
import com.coal.black.bc.socket.dto.ServerReturnFlagDto;
import com.coal.black.bc.socket.dto.TaskDto;
import com.coal.black.bc.socket.dto.TaskQueryDto;
import com.coal.black.bc.socket.utils.InputStreamUtils;

public class TaskQueryDeal {
	public TaskQueryResult deal(ClientInfoDto clientDto, List<IDtoBase> dtoList, InputStream in, OutputStream out) {
		try {
			TaskQueryDto queryDto = (TaskQueryDto) dtoList.get(0);
			byte[] queryTaskBytes = TaskQueryDtoCoder.toWire(queryDto);// 转换为QueryDto信息
			clientDto.setDataLength(queryTaskBytes.length);

			byte[] clientBytes = ClientInfoDtoCoder.toWire(clientDto);
			out.write(clientBytes);// 向客户端写入clientBytes
			out.write(queryTaskBytes);// 向客户端写入查询的条件
			out.flush();

			byte[] serverFlageBytes = InputStreamUtils.readFixedLengthData(ServerReturnFlagDto.bytesLength, in);// 获取服务器端的数据
			ServerReturnFlagDto returnFlag = ServerReturnFlagCoder.fromWire(serverFlageBytes);
			TaskQueryResult result = new TaskQueryResult();
			if (returnFlag.isSuccess()) {
				int dataLength = returnFlag.getDataLength();// 获取数据的长度
				byte data[] = InputStreamUtils.readFixedLengthData(dataLength, in);// 直接读取数据
				List<TaskDto> taskDtoList = TaskDtoListCoder.fromWire(data);// 从数据中反序列化
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

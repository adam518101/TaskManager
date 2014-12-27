package com.coal.black.bc.socket.client.deal;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.returndto.TaskQueryByTaskIDResult;
import com.coal.black.bc.socket.coder.ClientInfoDtoCoder;
import com.coal.black.bc.socket.coder.ServerReturnFlagCoder;
import com.coal.black.bc.socket.coder.TaskDtoCoder;
import com.coal.black.bc.socket.coder.TaskQueryByTaskIDDtoCoder;
import com.coal.black.bc.socket.dto.ClientInfoDto;
import com.coal.black.bc.socket.dto.ServerReturnFlagDto;
import com.coal.black.bc.socket.dto.TaskDto;
import com.coal.black.bc.socket.dto.TaskQueryByTaskIDDto;
import com.coal.black.bc.socket.utils.InputStreamUtils;

public class TaskQueryByTaskIDDeal {
	public TaskQueryByTaskIDResult deal(ClientInfoDto clientDto, List<IDtoBase> dtoList, InputStream in, OutputStream out) {
		try {
			TaskQueryByTaskIDDto queryDto = (TaskQueryByTaskIDDto) dtoList.get(0);
			byte[] queryTaskBytes = TaskQueryByTaskIDDtoCoder.toWire(queryDto);// 转换为QueryDto信息
			clientDto.setDataLength(queryTaskBytes.length);

			byte[] clientBytes = ClientInfoDtoCoder.toWire(clientDto);
			out.write(clientBytes);// 向客户端写入clientBytes
			out.write(queryTaskBytes);// 向客户端写入查询的条件
			out.flush();

			byte[] serverFlageBytes = InputStreamUtils.readFixedLengthData(ServerReturnFlagDto.bytesLength, in);// 获取服务器端的数据
			ServerReturnFlagDto returnFlag = ServerReturnFlagCoder.fromWire(serverFlageBytes);
			TaskQueryByTaskIDResult result = new TaskQueryByTaskIDResult();
			if (returnFlag.isSuccess()) {
				int dataLength = returnFlag.getDataLength();// 获取数据的长度
				byte data[] = InputStreamUtils.readFixedLengthData(dataLength, in);// 直接读取数据
				TaskDto taskDto = TaskDtoCoder.fromWire(data);
				result.setTaskDto(taskDto);
				result.setBusException(false);
				result.setSuccess(true);
				result.setBusinessErrorCode((byte) -1);
				result.setThrowable(null);
			} else {
				result.setSuccess(false);
				result.setBusException(returnFlag.isBusinessException());
				result.setBusinessErrorCode(returnFlag.getExceptionCode());
				result.setThrowable(null);
				result.setTaskDto(null);
			}
			return result;
		} catch (Throwable ex) {
			TaskQueryByTaskIDResult result = new TaskQueryByTaskIDResult();
			result.setSuccess(false);
			result.setBusException(false);
			result.setBusinessErrorCode((byte) -1);
			result.setThrowable(ex);
			result.setTaskDto(null);
			return result;
		}
	}
}

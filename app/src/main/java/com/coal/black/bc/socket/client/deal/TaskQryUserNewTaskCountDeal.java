package com.coal.black.bc.socket.client.deal;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.returndto.TaskQryUserNewTaskCountResult;
import com.coal.black.bc.socket.coder.ClientInfoDtoCoder;
import com.coal.black.bc.socket.coder.ServerReturnFlagCoder;
import com.coal.black.bc.socket.coder.TaskQryUserNewTaskDtoCoder;
import com.coal.black.bc.socket.dto.ClientInfoDto;
import com.coal.black.bc.socket.dto.ServerReturnFlagDto;
import com.coal.black.bc.socket.dto.TaskQryUserNewTaskDto;
import com.coal.black.bc.socket.utils.DataUtil;
import com.coal.black.bc.socket.utils.InputStreamUtils;

public class TaskQryUserNewTaskCountDeal {
	public TaskQryUserNewTaskCountResult deal(ClientInfoDto clientDto, List<IDtoBase> dtoList, InputStream in, OutputStream out) {
		try {
			TaskQryUserNewTaskDto dto = (TaskQryUserNewTaskDto) dtoList.get(0);
			byte[] dtoBytes = TaskQryUserNewTaskDtoCoder.toWire(dto);
			clientDto.setDataLength(dtoBytes.length);

			byte[] clientBytes = ClientInfoDtoCoder.toWire(clientDto);
			out.write(clientBytes);// 向客户端写入clientBytes
			out.write(dtoBytes);// 向客户端写入用户状态改变的Dto
			out.flush();

			byte[] serverFlageBytes = InputStreamUtils.readFixedLengthData(ServerReturnFlagDto.bytesLength, in);
			ServerReturnFlagDto returnFlag = ServerReturnFlagCoder.fromWire(serverFlageBytes);
			TaskQryUserNewTaskCountResult result = new TaskQryUserNewTaskCountResult();
			if (returnFlag.isSuccess()) {
				byte[] countBytes = InputStreamUtils.readFixedLengthData(4, in);
				int count = DataUtil.bytes2Int(countBytes);
				result.setBusException(false);
				result.setSuccess(true);
				result.setBusinessErrorCode((byte) -1);
				result.setThrowable(null);
				result.setCount(count);
			} else {
				result.setSuccess(false);
				result.setBusException(returnFlag.isBusinessException());
				result.setBusinessErrorCode(returnFlag.getExceptionCode());
				result.setThrowable(null);
				result.setCount(0);
			}
			return result;
		} catch (Throwable ex) {
			TaskQryUserNewTaskCountResult result = new TaskQryUserNewTaskCountResult();
			result.setSuccess(false);
			result.setBusException(false);
			result.setBusinessErrorCode((byte) -1);
			result.setThrowable(ex);
			return result;
		}
	}
}

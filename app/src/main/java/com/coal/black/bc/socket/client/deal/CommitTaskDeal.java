package com.coal.black.bc.socket.client.deal;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.returndto.CommitTaskResult;
import com.coal.black.bc.socket.coder.ClientInfoDtoCoder;
import com.coal.black.bc.socket.coder.CommitTaskDtoCoder;
import com.coal.black.bc.socket.coder.ServerReturnFlagCoder;
import com.coal.black.bc.socket.dto.ClientInfoDto;
import com.coal.black.bc.socket.dto.CommitTaskDto;
import com.coal.black.bc.socket.dto.ServerReturnFlagDto;
import com.coal.black.bc.socket.utils.InputStreamUtils;

public class CommitTaskDeal {
	public CommitTaskResult deal(ClientInfoDto clientDto, List<IDtoBase> dtoList, InputStream input, OutputStream output) {
		try {
			CommitTaskDto commitTaskDto = (CommitTaskDto) dtoList.get(0);
			byte[] bytes = CommitTaskDtoCoder.toWire(commitTaskDto);
			clientDto.setDataLength(bytes.length);

			byte[] clientBytes = ClientInfoDtoCoder.toWire(clientDto);

			output.write(clientBytes);
			output.write(bytes);// 写入登陆的流信息
			output.flush();

			byte[] serverFlageBytes = InputStreamUtils.readFixedLengthData(ServerReturnFlagDto.bytesLength, input);// 获取返回结果
			ServerReturnFlagDto returnFlag = ServerReturnFlagCoder.fromWire(serverFlageBytes);
			CommitTaskResult changeResult = new CommitTaskResult();
			if (returnFlag.isSuccess()) {
				changeResult.setSuccess(true);
				changeResult.setBusException(false);
				changeResult.setBusinessErrorCode((byte) -1);
				changeResult.setThrowable(null);
			} else {
				changeResult.setSuccess(false);
				changeResult.setBusException(returnFlag.isBusinessException());
				changeResult.setBusinessErrorCode((byte) (changeResult.isBusException() ? returnFlag.getExceptionCode() : -1));
				changeResult.setThrowable(null);
			}
			return changeResult;
		} catch (Exception ex) {
			CommitTaskResult result = new CommitTaskResult();
			result.setSuccess(false);
			result.setBusException(false);
			result.setBusinessErrorCode((byte) -1);
			result.setThrowable(ex);
			return result;
		}
	}
}

package com.coal.black.bc.socket.client.deal;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.returndto.UserTaskStatusChangeResult;
import com.coal.black.bc.socket.coder.ClientInfoDtoCoder;
import com.coal.black.bc.socket.coder.ServerReturnFlagCoder;
import com.coal.black.bc.socket.coder.UserTaskStatusChangeDtoCoder;
import com.coal.black.bc.socket.dto.ClientInfoDto;
import com.coal.black.bc.socket.dto.ServerReturnFlagDto;
import com.coal.black.bc.socket.dto.UserTaskStatusChangeDto;

public class UserTaskStatusChangeDeal {
	public UserTaskStatusChangeResult deal(ClientInfoDto clientDto, List<IDtoBase> dtoList, InputStream in, OutputStream out) {
		try {
			UserTaskStatusChangeDto utsc = (UserTaskStatusChangeDto) dtoList.get(0);
			byte[] utsBytes = UserTaskStatusChangeDtoCoder.toWire(utsc);
			clientDto.setDataLength(utsBytes.length);

			byte[] clientBytes = ClientInfoDtoCoder.toWire(clientDto);
			out.write(clientBytes);// 向客户端写入clientBytes
			out.write(utsBytes);// 向客户端写入用户状态改变的Dto
			out.flush();

			byte[] serverFlageBytes = new byte[ServerReturnFlagDto.bytesLength];
			in.read(serverFlageBytes, 0, ServerReturnFlagDto.bytesLength);
			ServerReturnFlagDto returnFlag = ServerReturnFlagCoder.fromWire(serverFlageBytes);
			UserTaskStatusChangeResult result = new UserTaskStatusChangeResult();
			if (returnFlag.isSuccess()) {
				result.setBusException(false);
				result.setSuccess(true);
				result.setBusinessErrorCode((byte) -1);
				result.setThrowable(null);
				return result;
			} else {
                Log.d("acmllaugh1", "deal (line 41): return flag is false.");
				result.setSuccess(false);
				result.setBusException(returnFlag.isBusinessException());
				result.setBusinessErrorCode(returnFlag.getExceptionCode());
				result.setThrowable(null);
                if (result.isBusException()) {
                    Log.d("acmllaugh1", "deal (line 46): result bus exception : " + result.getBusinessErrorCode());
                }else{
                    Log.d("acmllaugh1", "deal (line 49): no bussiness exception found.");
                }

			}
			return result;
		} catch (Throwable ex) {
			UserTaskStatusChangeResult result = new UserTaskStatusChangeResult();
			result.setSuccess(false);
			result.setBusException(false);
			result.setBusinessErrorCode((byte) -1);
			result.setThrowable(ex);
			return result;
		}
	}
}


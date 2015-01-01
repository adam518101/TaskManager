package com.coal.black.bc.socket.client.deal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.returndto.LoginResult;
import com.coal.black.bc.socket.coder.ClientInfoDtoCoder;
import com.coal.black.bc.socket.coder.LoginDtoCoder;
import com.coal.black.bc.socket.coder.ServerReturnFlagCoder;
import com.coal.black.bc.socket.dto.ClientInfoDto;
import com.coal.black.bc.socket.dto.LoginDto;
import com.coal.black.bc.socket.dto.ServerReturnFlagDto;
import com.coal.black.bc.socket.utils.DataUtil;
import com.coal.black.bc.socket.utils.InputStreamUtils;

public class LoginDeal {
	public LoginResult deal(ClientInfoDto clientDto, List<IDtoBase> dtoList, InputStream input, OutputStream output) throws Throwable {
		try {
			LoginDto loginDto = (LoginDto) dtoList.get(0);
			byte[] loginBytes = LoginDtoCoder.toWire(loginDto);
			clientDto.setDataLength(loginBytes.length);
			byte[] clientBytes = ClientInfoDtoCoder.toWire(clientDto);

			output.write(clientBytes);
			output.write(loginBytes);// 写入登陆的流信息
			output.flush();

			byte[] serverFlageBytes = InputStreamUtils.readFixedLengthData(ServerReturnFlagDto.bytesLength, input);// 获取返回结果
			ServerReturnFlagDto returnFlag = ServerReturnFlagCoder.fromWire(serverFlageBytes);
			LoginResult loginResult = new LoginResult();
			if (returnFlag.isSuccess()) {
				byte[] userIdBytes = InputStreamUtils.readFixedLengthData(4, input);// 获取用户ID的字节数组
				int userId = DataUtil.bytes2Int(userIdBytes);
				if (userId > 0) {
					ClientGlobal.setUserId(userId);
				}
				loginResult.setSuccess(true);
				loginResult.setBusException(false);
				loginResult.setUserId(userId);
				loginResult.setBusinessErrorCode((byte) -1);
				loginResult.setThrowable(null);
			} else {
				loginResult.setSuccess(false);
				loginResult.setBusException(returnFlag.isBusinessException());
				loginResult.setBusinessErrorCode((byte) (loginResult.isBusException() ? returnFlag.getExceptionCode() : -1));
				loginResult.setUserId(-1);
				loginResult.setThrowable(null);
			}
			return loginResult;
		} catch (IOException ex) {
			LoginResult loginResult = new LoginResult();
			loginResult.setSuccess(false);
			loginResult.setBusException(false);
			loginResult.setBusinessErrorCode((byte) -1);
			loginResult.setUserId(-1);
			loginResult.setThrowable(ex);
			return loginResult;
		}
	}
}

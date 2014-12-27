package com.coal.black.bc.socket.client.deal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.returndto.SignInResult;
import com.coal.black.bc.socket.coder.ClientInfoDtoCoder;
import com.coal.black.bc.socket.coder.ServerReturnFlagCoder;
import com.coal.black.bc.socket.coder.SignInDtoCoder;
import com.coal.black.bc.socket.dto.ClientInfoDto;
import com.coal.black.bc.socket.dto.ServerReturnFlagDto;
import com.coal.black.bc.socket.dto.SignInDto;
import com.coal.black.bc.socket.utils.InputStreamUtils;

/**
 * SignIn的客户端通信
 * 
 * @author wanghui-bc
 *
 */
public class SignInDeal {
	public SignInResult deal(ClientInfoDto clientDto, List<IDtoBase> dtoList, InputStream input, OutputStream output) {
		try {
			clientDto.setDataLength(dtoList.size() * SignInDto.bytesLength);
			byte[] clientBytes = ClientInfoDtoCoder.toWire(clientDto);
			output.write(clientBytes);
			for (IDtoBase dto : dtoList) {
				byte[] signInbytes = SignInDtoCoder.toWire((SignInDto) dto);
				output.write(signInbytes);
			}
			byte[] serverFlageBytes = InputStreamUtils.readFixedLengthData(ServerReturnFlagDto.bytesLength, input);// 获取服务器返回的数据信息
			ServerReturnFlagDto returnFlag = ServerReturnFlagCoder.fromWire(serverFlageBytes);
			SignInResult result = new SignInResult();
			if (returnFlag.isSuccess()) {
				byte[] returnBytes = InputStreamUtils.readFixedLengthData(returnFlag.getDataLength(), input);// 获取返回数据信息
				result.setSuccess(true);
				result.setBusException(false);
				result.setBusinessErrorCode((byte) -1);
				result.setThrowable(null);
				result.clearResultList();
				for (int i = 0; i < returnBytes.length; i++) {
					result.addResult((returnBytes[i] == 1 ? true : false));
				}
			} else {
				result.setSuccess(false);
				result.setBusException(returnFlag.isBusinessException());
				result.setBusinessErrorCode(returnFlag.getExceptionCode());
				result.setThrowable(null);
				result.clearResultList();
			}
			return result;
		} catch (IOException ex) {
			SignInResult result = new SignInResult();
			result.setSuccess(false);
			result.setBusException(false);
			result.setBusinessErrorCode((byte) -1);
			result.setThrowable(ex);
			result.clearResultList();
			return result;
		}
	}
}

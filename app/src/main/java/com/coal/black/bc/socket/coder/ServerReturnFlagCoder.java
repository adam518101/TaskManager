package com.coal.black.bc.socket.coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.coal.black.bc.socket.Constants;
import com.coal.black.bc.socket.dto.ServerReturnFlagDto;
import com.coal.black.bc.socket.exception.BusinessException;

/**
 * 服务器端返回给客户端的Dto信息
 * 
 * @author wanghui-bc
 *
 */
public class ServerReturnFlagCoder {
	public static byte[] toWire(ServerReturnFlagDto serverReturnDto) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		try {
			out.write(serverReturnDto.isSuccess() ? 1 : 0);
			out.write(serverReturnDto.isHasException() ? 1 : 0);
			out.write(serverReturnDto.isBusinessException() ? 1 : 0);
			out.write(serverReturnDto.getExceptionCode());
			dout.writeInt(serverReturnDto.getDataLength());
			return out.toByteArray();
		} catch (IOException e) {
			throw new BusinessException(Constants.SERVER_RETURN_FLAG_CODER_TO_WIRE_ERROR, e);
		}
	}

	public static ServerReturnFlagDto fromWire(byte[] bytes) {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(in);
		try {
			byte isSuccess = din.readByte();
			byte hasException = din.readByte();
			byte isBusinessException = din.readByte();
			byte exceptionCode = din.readByte();
			int length = din.readInt();
			ServerReturnFlagDto srf = new ServerReturnFlagDto();
			srf.setDataLength(length);
			srf.setSuccess(isSuccess == 1);
			srf.setHasException(hasException == 1);
			srf.setExceptionCode(exceptionCode);
			srf.setBusinessException(isBusinessException == 1);
			return srf;
		} catch (IOException ex) {
			throw new BusinessException(Constants.SERVER_RETURN_FLAG_FORM_WIRE_ERROR, ex);
		}
	}
}

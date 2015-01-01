package com.coal.black.bc.socket.client.deal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.returndto.ChangePwdResult;
import com.coal.black.bc.socket.coder.ChangePwdDtoCoder;
import com.coal.black.bc.socket.coder.ClientInfoDtoCoder;
import com.coal.black.bc.socket.coder.ServerReturnFlagCoder;
import com.coal.black.bc.socket.dto.ChangePwdDto;
import com.coal.black.bc.socket.dto.ClientInfoDto;
import com.coal.black.bc.socket.dto.ServerReturnFlagDto;
import com.coal.black.bc.socket.utils.InputStreamUtils;

/**
 * 修改密码
 * 
 * @author wanghui-bc
 *
 */
public class ChangePwdDeal {
	/**
	 * 修改用户密码
	 * 
	 * @param clientDto
	 * @param dtoList
	 * @param input
	 * @param output
	 * @return
	 * @throws Throwable
	 */
	public ChangePwdResult deal(ClientInfoDto clientDto, List<IDtoBase> dtoList, InputStream input, OutputStream output) throws Throwable {
		try {
			ChangePwdDto cpd = (ChangePwdDto) dtoList.get(0);
			byte[] changePwdBytes = ChangePwdDtoCoder.toWire(cpd);
			clientDto.setDataLength(changePwdBytes.length);

			byte[] clientBytes = ClientInfoDtoCoder.toWire(clientDto);

			output.write(clientBytes);
			output.write(changePwdBytes);// 写入登陆的流信息
			output.flush();

			byte[] serverFlageBytes = InputStreamUtils.readFixedLengthData(ServerReturnFlagDto.bytesLength, input);// 获取返回结果
			ServerReturnFlagDto returnFlag = ServerReturnFlagCoder.fromWire(serverFlageBytes);
			ChangePwdResult changeResult = new ChangePwdResult();
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
		} catch (IOException ex) {
			ChangePwdResult result = new ChangePwdResult();
			result.setSuccess(false);
			result.setBusException(false);
			result.setBusinessErrorCode((byte) -1);
			result.setThrowable(ex);
			return result;
		}
	}
}

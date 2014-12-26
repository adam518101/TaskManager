package com.coal.black.bc.socket.coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.coal.black.bc.socket.Constants;
import com.coal.black.bc.socket.dto.ClientInfoDto;
import com.coal.black.bc.socket.enums.OperateType;
import com.coal.black.bc.socket.exception.BusinessException;

public class ClientInfoDtoCoder {
	public static byte[] toWire(ClientInfoDto clientInfo) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		try {
			dout.write(clientInfo.getBeginFlag());// 先写入开始的标记信息
			dout.writeInt(clientInfo.getUserId());// 再写入用户id
			dout.write(clientInfo.getMac());// 再写入mac地址
			dout.write(clientInfo.getOperateType().getValue());// 在写入操作的类型信息
			dout.writeInt(clientInfo.getDataLength());// 再次写入数据长度
		} catch (IOException ex) {
			throw new BusinessException(Constants.CLIENT_INFO_CODER_TO_WIRE_ERROR, ex);
		}
		return out.toByteArray();
	}

	public static ClientInfoDto fromWire(byte[] bytes) {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(in);
		try {
			byte[] beginFlag = new byte[4];
			din.read(beginFlag, 0, 4);

			int userId = din.readInt();

			byte[] mac = new byte[6];
			din.read(mac, 0, 6);

			byte otype = din.readByte();
			OperateType operateType = OperateType.getOperateType(otype);

			int dataLength = din.readInt();

			ClientInfoDto ci = new ClientInfoDto();

			ci.setBeginFlag(beginFlag);
			ci.setUserId(userId);
			ci.setMac(mac);
			ci.setOperateType(operateType);
			ci.setDataLength(dataLength);
			return ci;
		} catch (IOException ex) {
			throw new BusinessException(Constants.CLIENT_INFO_CODER_FORM_WIRE_ERROR, ex);
		}
	}
}

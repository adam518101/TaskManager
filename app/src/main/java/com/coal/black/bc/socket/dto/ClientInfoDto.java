package com.coal.black.bc.socket.dto;

import com.coal.black.bc.socket.Constants;
import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.enums.OperateType;
import com.coal.black.bc.socket.utils.CommonUtils;

public class ClientInfoDto extends IDtoBase {
	public static final int bytesLength = 19;// 对应的bytesLength信息

	private static final long serialVersionUID = -8129186353631395325L;
	private byte[] beginFlag = Constants.CLIENT_BEGIN_FLAG;// 开始的标记信息(4个字节）
	private byte[] mac;// 获取mac信息，6个字节
	private int userId;// 获取用户id 4个字节
	private OperateType operateType;// 操作类型 1个字节
	private int dataLength = 0;// 数据长度信息 4个字节

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte[] getMac() {
		return mac;
	}

	public void setMac(byte[] mac) {
		this.mac = mac;
	}

	public OperateType getOperateType() {
		return operateType;
	}

	public void setOperateType(OperateType operateType) {
		this.operateType = operateType;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public byte[] getBeginFlag() {
		return beginFlag;
	}

	public void setBeginFlag(byte[] beginFlag) {
		this.beginFlag = beginFlag;
	}

	public String getMacAddress() {
		return CommonUtils.bytes2MacString(mac);
	}

	public String toString() {
		return "ClientInfoDto [beginFlag=" + String.valueOf(beginFlag) + ", mac=" + CommonUtils.bytes2MacString(mac) + ", userId=" + userId + ", operateType="
				+ operateType + ", dataLength=" + dataLength + "]";
	}
}

package com.coal.black.bc.socket.enums;

public enum OperateType {
	Error((byte) 0), // 表示错误的状态
	LoginIn((byte) 1), // 登陆
	SingIn((byte) 2), // 签到
	TaskQuery((byte) 3), // 接收任务
	UploadFile((byte) 4);// 上传文件

	private byte value;

	private OperateType(byte value) {
		this.value = value;
	}

	public byte getValue() {
		return value;
	}

	public static OperateType getOperateType(byte value) {
		switch (value) {
		case 1:
			return LoginIn;
		case 2:
			return SingIn;
		case 3:
			return TaskQuery;
		case 4:
			return UploadFile;
		default:
			return Error;
		}
	}
}

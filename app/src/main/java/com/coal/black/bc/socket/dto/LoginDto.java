package com.coal.black.bc.socket.dto;

import com.coal.black.bc.socket.IDtoBase;

public class LoginDto extends IDtoBase {
	private static final long serialVersionUID = 5004346148380748890L;

	private String userCode;// 没有加密之前的密码
	private String md5Pwd;// md5密码

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getMd5Pwd() {
		return md5Pwd;
	}

	public void setMd5Pwd(String md5Pwd) {
		this.md5Pwd = md5Pwd;
	}

	public String toString() {
		return "LoginDto [userCode=" + userCode + ", md5Pwd=" + md5Pwd + "]";
	}

}

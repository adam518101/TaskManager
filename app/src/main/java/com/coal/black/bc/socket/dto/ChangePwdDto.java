package com.coal.black.bc.socket.dto;

import com.coal.black.bc.socket.IDtoBase;

public class ChangePwdDto extends IDtoBase {
	private static final long serialVersionUID = 4583791122525752956L;

	private transient String oldPwd;// 旧的密码，是指没有MD5之前的密码
	private transient String newPwd;// 新密码，指的是没有MD5之前的密码

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
}

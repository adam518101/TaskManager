package com.coal.black.bc.socket.coder;

import com.coal.black.bc.socket.dto.LoginDto;

/**
 * 登陆的Coder和Encoder
 * 
 * @author wanghui-bc
 *
 */
public class LoginDtoCoder {
	public static byte[] toWire(LoginDto loginDto) {
		byte[] userCodeBytes = loginDto.getUserCode().getBytes();// 获取用户Coder对应的字节数目
		byte[] pwdBytes = loginDto.getMd5Pwd().getBytes();
		byte[] arrays = new byte[userCodeBytes.length + pwdBytes.length];
		System.arraycopy(userCodeBytes, 0, arrays, 0, userCodeBytes.length);
		System.arraycopy(pwdBytes, 0, arrays, userCodeBytes.length, pwdBytes.length);
		return arrays;
	}

	public static LoginDto fromWire(byte[] bytes) {
		byte[] userCodeBytes = new byte[bytes.length - 32];
		System.arraycopy(bytes, 0, userCodeBytes, 0, bytes.length - 32);
		byte[] md5PwdBytes = new byte[32];
		System.arraycopy(bytes, userCodeBytes.length, md5PwdBytes, 0, 32);
		String userCode = new String(userCodeBytes);
		String md5Pwd = new String(md5PwdBytes);
		LoginDto loginDto = new LoginDto();
		loginDto.setUserCode(userCode);
		loginDto.setMd5Pwd(md5Pwd);
		return loginDto;
	}
}

package com.coal.black.bc.socket.coder;

import com.coal.black.bc.socket.Constants;
import com.coal.black.bc.socket.dto.ChangePwdDto;
import com.coal.black.bc.socket.exception.BusinessException;
import com.coal.black.bc.socket.utils.SecurityUtil;

public class ChangePwdDtoCoder {
	public static byte[] toWire(ChangePwdDto changePwdDto) {
		try {
			String oldMd5 = SecurityUtil.encodeByMD5(changePwdDto.getOldPwd());
			String newMd5 = SecurityUtil.encodeByMD5(changePwdDto.getNewPwd());
			byte[] oldBytes = oldMd5.getBytes();
			byte[] newBytes = newMd5.getBytes();
			byte[] bytes = new byte[oldBytes.length + newBytes.length];
			System.arraycopy(oldBytes, 0, bytes, 0, oldBytes.length);
			System.arraycopy(newBytes, 0, bytes, oldBytes.length, newBytes.length);
			return bytes;
		} catch (Exception ex) {
			throw new BusinessException(Constants.CHANGE_PWD_CODER_TO_WIRE_ERROR, ex);
		}
	}

	public static ChangePwdDto fromWire(byte[] bytes) {
		try {
			byte[] oldBytes = new byte[32];
			byte[] newBytes = new byte[32];
			System.arraycopy(bytes, 0, oldBytes, 0, 32);
			System.arraycopy(bytes, 32, newBytes, 0, 32);
			String oldStr = new String(oldBytes);
			String newStr = new String(newBytes);
			ChangePwdDto cpd = new ChangePwdDto();
			cpd.setOldPwd(oldStr);
			cpd.setNewPwd(newStr);
			return cpd;
		} catch (Exception ex) {
			throw new BusinessException(Constants.CHANGE_PWD_CODER_FORM_WIRE_ERROR, ex);
		}
	}
}

package com.coal.black.bc.socket.dto;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.exception.ExceptionBase;

/**
 * 服务器端返回给客户端的标识的Dto信息
 * 
 * @author wanghui-bc
 *
 */
public class ServerReturnFlagDto extends IDtoBase {
	public static int bytesLength = 8;

	private static final long serialVersionUID = -4826783202195439716L;
	private boolean hasException;// 是否有异常信息
	private boolean isBusinessException;// 是否是业务异常
	private boolean isSuccess;
	private byte exceptionCode = ExceptionBase.NONE_EXCEPTION;// 对应到exceptionUtil中的异常信息
	private int dataLength;// 返回的数据长度（就是跟在前面三个对象之后的信息

	public boolean isHasException() {
		return hasException;
	}

	public void setHasException(boolean hasException) {
		this.hasException = hasException;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public byte getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(byte exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public boolean isBusinessException() {
		return isBusinessException;
	}

	public void setBusinessException(boolean isBusinessException) {
		this.isBusinessException = isBusinessException;
	}
}

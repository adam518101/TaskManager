package com.coal.black.bc.socket.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -6509012568501568562L;

	private String code = null;

	public BusinessException(String code) {
		this.code = code;
	}

	public BusinessException(String code, Throwable t) {
		super(t);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

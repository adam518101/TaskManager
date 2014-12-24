package com.coal.black.bc.socket.enums;

public enum SignInType {
	SignIn(1), SignOut(2), ReportPosition(3);

	private int _value;

	private SignInType(int value) {
		_value = value;
	}

	public int value() {
		return _value;
	}

	public static SignInType getSignType(int i) {
		switch (i) {
		case 1:
			return SignIn;
		case 2:
			return SignOut;
		default:
			return ReportPosition;
		}
	}
}

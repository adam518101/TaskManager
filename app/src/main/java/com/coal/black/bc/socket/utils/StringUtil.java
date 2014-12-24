package com.coal.black.bc.socket.utils;

public class StringUtil {
	private static final String EMPTY_STR = "";

	public static boolean isEmpty(String str) {
		return str == null || str.trim().equals(EMPTY_STR);
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
}

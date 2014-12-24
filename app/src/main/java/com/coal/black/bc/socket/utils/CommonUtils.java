package com.coal.black.bc.socket.utils;

import com.coal.black.bc.socket.Constants;
import com.coal.black.bc.socket.exception.BusinessException;

public class CommonUtils {
	public static boolean byteArrayEquals(byte[] a, byte[] b) {
		if (a == null && b == null) {
			return true;
		}
		if (a.length == b.length) {
			boolean equals = true;
			for (int i = 0; i < a.length; i++) {
				if (a[i] != b[i]) {
					equals = false;
					break;
				}
			}
			return equals;
		}
		return false;
	}

	public static byte[] int2Bytes(int value) {
		byte[] src = new byte[4];
		src[3] = (byte) ((value >> 24) & 0xFF);
		src[2] = (byte) ((value >> 16) & 0xFF);
		src[1] = (byte) ((value >> 8) & 0xFF);
		src[0] = (byte) (value & 0xFF);
		return src;
	}

	public static int bytes2Int(byte[] src) {
		int offset = 0;
		int value;
		value = (int) ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8) | ((src[offset + 2] & 0xFF) << 16) | ((src[offset + 3] & 0xFF) << 24));
		return value;
	}

	/**
	 * 字节转换为mac地址，返回的字符串以":"隔开，并且是大写形式
	 * 
	 * @param macBytes
	 * @return
	 */
	public static String bytes2MacString(byte[] macBytes) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < macBytes.length; i++) {
			if (i != 0) {
				builder.append(":");
			}
			String first = Integer.toHexString(macBytes[i] & 0xF0);
			String last = Integer.toHexString(macBytes[i] & 0x0F);
			if (first.length() > 1) {
				first = first.substring(0, 1);
			}
			if (last.length() > 0) {
				last = last.substring(last.length() - 1);
			}
			builder.append(first);
			builder.append(last);
		}
		return builder.toString().toUpperCase();
	}

	/**
	 * mac地址转换为字节序列
	 * 
	 * @param macStr
	 *            以":"隔开
	 * @return
	 */
	public static byte[] macString2Bytes(String macStr) {
		String[] str = macStr.split(":");
		StringBuilder builder = new StringBuilder();
		for (String s : str) {
			builder.append(s);
		}
		String hexString = builder.toString();
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		boolean notEffective = false;
		if (hexChars.length != 12) {
			notEffective = true;
		}
		for (char c : hexChars) {
			if (!charIsEffective(c)) {// 如果字符串无效
				notEffective = true;
				break;
			}
		}
		if (notEffective) {
			throw new BusinessException(Constants.MAC_STRING_NOT_EFFECTIVE);// 无效的字符串信息
		} else {
			byte[] d = new byte[length];
			for (int i = 0; i < length; i++) {
				int pos = i * 2;
				d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
			}
			return d;
		}
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	private static boolean charIsEffective(char c) {
		return "0123456789ABCDEF".indexOf(c) > -1;
	}
}

package com.coal.black.bc.socket.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataUtil {
	public static byte[] int2Bytes(int i) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		try {
			dout.writeInt(i);
		} catch (IOException e) {
			return null;
		}
		return out.toByteArray();
	}

	public static int bytes2Int(byte[] bytes) {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(in);
		try {
			return din.readInt();
		} catch (IOException ex) {
			return -1;
		}
	}
}

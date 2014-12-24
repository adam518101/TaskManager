package com.coal.black.bc.socket.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import com.coal.black.bc.socket.utils.CommonUtils;

public class ClientGlobal {
	// public static String serverIp = "127.0.0.1";
	public static String serverIp = "223.68.133.15";
	public static int serverPort = 12323;
	public static int socketTimeOut = 30000;

	public static SocketAddress address;

	public static String macStr;// mac地址字符串（以":"分割）
	public static int userId = 0;
	public static byte[] macBytes;
	static {
		address = new InetSocketAddress(serverIp, serverPort);
		macStr = "7C:E9:D3:EF:FA:10";
		macBytes = CommonUtils.macString2Bytes(macStr);
	}
}

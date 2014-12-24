package com.coal.black.bc.socket;

import com.coal.black.bc.socket.config.PropertiesConfig;

public class ServerGlobal {
	public static int socketTimeOut;
	public static int port;
	public static String fileBasePath;
	static {
		port = Integer.parseInt(PropertiesConfig.getServerConfig("server.port", "12345"));
		socketTimeOut = Integer.parseInt(PropertiesConfig.getServerConfig("socket.timeout", "30000"));
		fileBasePath = PropertiesConfig.getServerConfig("upload.file.base.path", "/BC_UPLOADFILES");
	}
}

package com.coal.black.bc.socket.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.coal.black.bc.socket.Constants;

public class PropertiesConfig {
	private static String bcHome;
	private static File clientConfig;
	private static File serverConfig;
	private static Properties clientProperties;
	private static Properties serverProperties;

	static {
		String bcHome1 = System.getProperty(Constants.BC_HOME_STR);
		if (bcHome1 == null || bcHome1.trim().length() <= 0) {
		}
		bcHome1 = System.getenv(Constants.BC_HOME_STR);
		if (bcHome1 == null || bcHome1.trim().length() <= 0) {
			throw new RuntimeException("No avaliable BC_HOME was founded");
		}
		File f = new File(bcHome1);
		if (f.exists() && f.isDirectory()) {
			bcHome = f.getAbsolutePath();
			clientConfig = new File(bcHome + File.separator + "socket/client.properties");
			serverConfig = new File(bcHome + File.separator + "socket/server.properties");
		} else {
			throw new RuntimeException(bcHome1 + " is not an effiective direcotry path");
		}
		if (clientConfig == null || clientConfig.isDirectory() || !clientConfig.exists()) {
			throw new RuntimeException(bcHome + File.separator + "socket/client.properties" + " is not an effiective properties file");
		} else {
			InputStream in = null;
			try {
				in = new FileInputStream(clientConfig);
				clientProperties = new Properties();
				clientProperties.load(in);
			} catch (Exception e) {

			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
					}
				}
			}
		}
		if (serverConfig == null || serverConfig.isDirectory() || !serverConfig.exists()) {
			throw new RuntimeException(bcHome + File.separator + "socket/server.properties" + " is not an effiective properties file");
		} else {
			InputStream in = null;
			try {
				in = new FileInputStream(serverConfig);
				serverProperties = new Properties();
				serverProperties.load(in);
			} catch (Exception e) {

			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}

	public static String getClientConfig(String key, String defaultValue) {
		return clientProperties.getProperty(key, defaultValue);
	}

	public static String getServerConfig(String key, String defaultValue) {
		return serverProperties.getProperty(key, defaultValue);
	}
}

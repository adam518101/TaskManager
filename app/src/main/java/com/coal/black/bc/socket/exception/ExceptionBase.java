package com.coal.black.bc.socket.exception;

public class ExceptionBase {
	public static byte NONE_EXCEPTION = 0;
	public static byte SERVER_INNER_EXCEPTION = 1;
	public static byte LOGIN_NOT_ALLOWNED = 2;
	public static byte DATA_LENGTH_NOT_SAME = 3;// 实际接收的数据的长度和ClientInfo中实际需要的数据长度不一致的情况下
	public static byte CLIENT_NO_DEAL_HANDLER = 4;// 客户端没有处理的handler，主要是值在SocketClient中没有加上此种类型的处理
}

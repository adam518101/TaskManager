package com.coal.black.bc.socket.client.handlers;

import java.util.ArrayList;
import java.util.List;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.SocketClient;
import com.coal.black.bc.socket.client.returndto.BasicResult;
import com.coal.black.bc.socket.client.returndto.UploadFileResult;
import com.coal.black.bc.socket.dto.UploadFileDto;
import com.coal.black.bc.socket.enums.OperateType;

public class UploadFileHandler {
	public boolean uploadedFinished = false;// 传输是否已经完成，主要是用来记录服务器端接收的情况的，这样可以让另外一个线程获取到目前的进展
	public int serverReceivedLength = 0;// 服务器端接收到的长度，主要是用来记录服务器端接收的情况的，这样可以让另外一个线程获取到目前的进展

	public UploadFileResult upload(UploadFileDto uploadDto) {
		SocketClient client = new SocketClient();
		List<IDtoBase> list = new ArrayList<IDtoBase>();
		list.add(uploadDto);
		BasicResult result = client.deal(OperateType.UploadFile, ClientGlobal.getUserId(), list, this);
		if (result instanceof UploadFileResult) {
			return (UploadFileResult) result;
		} else {
			UploadFileResult uploadFileResult = new UploadFileResult(result);
			return uploadFileResult;
		}
	}
}

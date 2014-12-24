package com.coal.black.bc.socket.client.test;

import java.io.File;

import com.coal.black.bc.socket.client.ClientGlobal;
import com.coal.black.bc.socket.client.handlers.UploadFileHandler;
import com.coal.black.bc.socket.client.returndto.UploadFileResult;
import com.coal.black.bc.socket.dto.UploadFileDto;

public class TestUploadFile {
	public static void main(String[] args) {
		File f = new File("D:\\Wildlife.wmv");
		UploadFileDto fileDto = new UploadFileDto();
		fileDto.setClientFile(f);
		fileDto.setTaskId(1);
		fileDto.setPicture(false);
		ClientGlobal.userId = 11;
		UploadFileHandler uh = new UploadFileHandler();
		UploadFileResult result = uh.upload(fileDto);
		if (result.isSuccess()) {
			System.out.println("Success, result is " + result.isUploadSuccess());
		} else {
			if (result.isBusException()) {
				System.out.println("Business Exception, exception code is " + result.getBusinessErrorCode());
			} else {
				System.out.println("Other Exception, exception type is " + result.getThrowable());
			}
		}
	}
}

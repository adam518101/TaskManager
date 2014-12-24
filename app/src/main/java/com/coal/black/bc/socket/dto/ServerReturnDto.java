package com.coal.black.bc.socket.dto;

public class ServerReturnDto {
	private byte[] datas;
	private ServerReturnFlagDto returnFlagDto;

	public byte[] getDatas() {
		return datas;
	}

	public void setDatas(byte[] datas) {
		this.datas = datas;
	}

	public ServerReturnFlagDto getReturnFlagDto() {
		return returnFlagDto;
	}

	public void setReturnFlagDto(ServerReturnFlagDto returnFlagDto) {
		this.returnFlagDto = returnFlagDto;
	}

}

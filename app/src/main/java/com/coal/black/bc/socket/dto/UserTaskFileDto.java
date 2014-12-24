package com.coal.black.bc.socket.dto;

import java.io.Serializable;

public class UserTaskFileDto implements Serializable {
	private static final long serialVersionUID = 6986855520005441813L;

	private int taskID;
	private int userID;
	private String fileName;
	private String filePath;
	private boolean isPicture;

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isPicture() {
		return isPicture;
	}

	public void setPicture(boolean isPicture) {
		this.isPicture = isPicture;
	}
}

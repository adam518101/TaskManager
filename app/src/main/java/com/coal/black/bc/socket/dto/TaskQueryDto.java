package com.coal.black.bc.socket.dto;

import com.coal.black.bc.socket.IDtoBase;

/**
 * 查询条件的Dto信息列表（这里主要是Andriod端的查询条件，只包括一个Status的数组，表示具体查询哪些状态的数据
 * 
 * @author wanghui-bc
 *
 */
public class TaskQueryDto extends IDtoBase {
	private static final long serialVersionUID = 2331519775295697239L;

	private int[] userTaskStatus;// 用户任务状态

	public int[] getUserTaskStatus() {
		return userTaskStatus;
	}

	public void setUserTaskStatus(int[] userTaskStatus) {
		this.userTaskStatus = userTaskStatus;
	}
}

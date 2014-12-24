package com.coal.black.bc.socket.coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import com.coal.black.bc.socket.Constants;
import com.coal.black.bc.socket.dto.TaskDto;
import com.coal.black.bc.socket.exception.BusinessException;
import com.coal.black.bc.socket.utils.StringUtil;

public class TaskCoder {
	public static byte[] toWire(TaskDto task) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		try {
			dout.writeInt(task.getId());// 写入ID

			writeStringData(task.getBank(), dout);// 写入银行
			writeStringData(task.getCaseID(), dout);// 写入个案序列号
			writeStringData(task.getName(), dout);// 写名称
			writeStringData(task.getIdentityCard(), dout);// 写入身份证号
			writeStringData(task.getBankCard(), dout);// 写入银行卡号

			dout.writeDouble(task.getCaseAmount());// 写入委案金额
			dout.writeDouble(task.getHasPayed());// 写入已还款

			writeStringData(task.getNoticeStatement(), dout);// 催款状态
			writeStringData(task.getAddress(), dout);// 写入地址
			writeStringData(task.getRegion(), dout);// 写入地区

			dout.writeInt(task.getVisitTimes());// 写入访问期次

			writeStringData(task.getVisitReason(), dout);// 写入外访原因
			writeStringData(task.getRequirement(), dout);// 写入要求
			writeStringData(task.getMemo(), dout);// 写入备注
			writeStringData(task.getContactInfo(), dout);// 写入所有联系方式
			writeStringData(task.getCompanyName(), dout);// 写入公司名称
			writeStringData(task.getVisitReport(), dout);// 写入访问报告

			writeDate(task.getEstimateVisitDate(), dout);// 写入预计访问日期
			writeDate(task.getRealVisitDate(), dout);// 写入实际访问日期
			writeDate(task.getReturnTime(), dout);// 写入实际返回时间

			dout.writeInt(task.getRealVisitUser());// 写入实际访问人
			dout.writeBoolean(task.isValid());
			dout.writeInt(task.getTaskStatus());
			dout.writeInt(task.getUserTaskStatus());
		} catch (IOException ex) {
			throw new BusinessException(Constants.TASK_CODER_TO_WIRE_ERROR, ex);
		}
		return out.toByteArray();
	}

	public static TaskDto fromWire(byte[] bytes) {
		TaskDto taskDto = new TaskDto();
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(in);

		try {
			int taskId = din.readInt();
			taskDto.setId(taskId);

			taskDto.setBank(getStringData(din));// 读取银行信息
			taskDto.setCaseID(getStringData(din));// 读取个案序列号
			taskDto.setName(getStringData(din));// 读取名称
			taskDto.setIdentityCard(getStringData(din));// 读取身份证号码
			taskDto.setBankCard(getStringData(din));// 读取银行卡号

			double caseAmount = din.readDouble();
			taskDto.setCaseAmount(caseAmount);// 读取委案金额
			double hasPayed = din.readDouble();
			taskDto.setHasPayed(hasPayed);// 写入已还款

			taskDto.setNoticeStatement(getStringData(din));// 读取催款状态
			taskDto.setAddress(getStringData(din));// 读取地址
			taskDto.setRegion(getStringData(din));// 写入地区

			int visitedTimes = din.readInt();
			taskDto.setVisitTimes(visitedTimes);

			taskDto.setVisitReason(getStringData(din));// 读取外访原因
			taskDto.setRequirement(getStringData(din));// 读取要求
			taskDto.setMemo(getStringData(din));// 读取备注
			taskDto.setContactInfo(getStringData(din));// 读取所有联系方式
			taskDto.setCompanyName(getStringData(din));// 读取公司名称
			taskDto.setVisitReport(getStringData(din));// 读取访问报告

			taskDto.setEstimateVisitDate(readDate(din));// 读取预计访问日期
			taskDto.setRealVisitDate(readDate(din));// 读取实际访问日期
			taskDto.setReturnTime(readDate(din));// 读取实际返回时间

			taskDto.setRealVisitUser(din.readInt());// 读取实际访问人
			taskDto.setValid(din.readBoolean());// 读取是否有效
			taskDto.setTaskStatus(din.readInt());// 设置任务的状态
			taskDto.setUserTaskStatus(din.readInt());// 设置用户任务状态
		} catch (IOException ex) {
			throw new BusinessException(Constants.TASK_CODER_FORM_WIRE_ERROR, ex);
		}
		return taskDto;
	}

	private static void writeStringData(String value, DataOutputStream dout) throws IOException {
		int length = 0;
		byte[] bytes = null;
		if (StringUtil.isNotEmpty(value)) {
			bytes = value.trim().getBytes();
			length = bytes.length;
		}
		dout.writeInt(length);
		if (length > 0) {
			dout.write(bytes);
		}
	}

	private static void writeDate(Date date, DataOutputStream dout) throws IOException {
		long time = 0;
		if (date != null) {
			time = date.getTime();
		}
		dout.writeLong(time);
	}

	private static String getStringData(DataInputStream din) throws IOException {
		int length = din.readInt();
		if (length == 0) {
			return null;
		}
		byte[] bytes = new byte[length];
		if (length == din.read(bytes, 0, length)) {
			String s = new String(bytes);
			return s;
		} else {
			throw new RuntimeException("The real bytes read length is not same with length " + length);
		}
	}

	private static Date readDate(DataInputStream din) throws IOException {
		long time = din.readLong();
		if (time <= 0) {
			return null;
		} else {
			return new Date(time);
		}
	}
}

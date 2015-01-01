package com.coal.black.bc.socket.coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import com.coal.black.bc.socket.Constants;
import com.coal.black.bc.socket.dto.CommitTaskDto;
import com.coal.black.bc.socket.exception.BusinessException;
import com.coal.black.bc.socket.utils.InputStreamUtils;

public class CommitTaskDtoCoder {
	public static byte[] toWire(CommitTaskDto commitTaskDto) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(out);
		try {
			dout.writeInt(commitTaskDto.getTaskId());
			dout.write(new byte[] { (byte) (commitTaskDto.isValid() ? 1 : 0) });
			dout.write(new byte[] { (byte) (commitTaskDto.isNeedVisitAgain() ? 1 : 0) });
			byte[] reportTasks = commitTaskDto.getVisitReport().getBytes("UTF-8");
			dout.write(reportTasks);
		} catch (IOException ex) {
			throw new BusinessException(Constants.COMMIT_TASK_CODER_TO_WIRE_ERROR, ex);
		}
		return out.toByteArray();
	}

	public static CommitTaskDto fromWire(byte[] bytes) {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream din = new DataInputStream(in);
		try {
			CommitTaskDto dto = new CommitTaskDto();
			dto.setTaskId(din.readInt());
			dto.setValid(din.read() == 1);
			dto.setNeedVisitAgain(din.read() == 1);
			byte[] reportBytes = InputStreamUtils.readFixedLengthData(bytes.length - 6, din);
			String visitReport = new String(reportBytes, "UTF-8");
			dto.setVisitReport(visitReport);
			return dto;
		} catch (IOException ex) {
			throw new BusinessException(Constants.COMMIT_TASK_CODER_FORM_WIRE_ERROR, ex);
		}
	}
}

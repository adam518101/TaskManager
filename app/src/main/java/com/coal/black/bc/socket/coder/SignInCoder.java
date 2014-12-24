package com.coal.black.bc.socket.coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.coal.black.bc.socket.Constants;
import com.coal.black.bc.socket.dto.SignInDto;
import com.coal.black.bc.socket.enums.SignInType;
import com.coal.black.bc.socket.exception.BusinessException;

public class SignInCoder {
	/**
	 * 把SignIn转换为字节流
	 * 
	 * @param singIn
	 * @return
	 */
	public static byte[] toWire(SignInDto signIn) {
		ByteArrayOutputStream outStream = null;
		DataOutputStream dataOut = null;
		try {
			outStream = new ByteArrayOutputStream();
			dataOut = new DataOutputStream(outStream);
			dataOut.writeDouble(signIn.getLongitude());
			dataOut.writeDouble(signIn.getLatitude());
			dataOut.writeLong(signIn.getTime());
			dataOut.write((byte) signIn.getType().value());
			return outStream.toByteArray();
		} catch (IOException ex) {
			throw new BusinessException(Constants.SING_IN_CODER_TO_WIRE_ERROR, ex);
		}
	}

	/**
	 * 把字节流转换为SignInDto
	 * 
	 * @param input
	 * @return
	 */
	public static SignInDto fromWire(byte[] input) {
		ByteArrayInputStream in = null;
		DataInputStream din = null;
		try {
			in = new ByteArrayInputStream(input);
			din = new DataInputStream(in);
			SignInDto si = new SignInDto();
			double longitude = din.readDouble();
			double latitude = din.readDouble();
			long time = din.readLong();
			byte type = din.readByte();
			si.setLongitude(longitude);
			si.setLatitude(latitude);
			si.setTime(time);
			si.setType(SignInType.getSignType((int) type));
			return si;
		} catch (IOException ex) {
			throw new BusinessException("SING_IN_CODER_FORM_WIRE_ERROR", ex);
		}
	}
}

package com.coal.black.bc.socket.dto;

import com.coal.black.bc.socket.IDtoBase;
import com.coal.black.bc.socket.enums.SignInType;

/**
 * 签到的Dto信息
 * 
 * @author wanghui-bc
 *
 */
public class SignInDto extends IDtoBase {
	public static int bytesLength = 25;
	private static final long serialVersionUID = 1859081682954601291L;
	private double longitude;// 经度 4个字节
	private double latitude;// 纬度 4个字节
	private long time;// 时间 8个字节
	private SignInType type;// 这里的length在传输的时候会转换成byte来进行传输，所以只有25的长度

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public SignInType getType() {
		return type;
	}

	public void setType(SignInType type) {
		this.type = type;
	}

	public String toString() {
		return "SignInDto [longitude=" + longitude + ", latitude=" + latitude + ", time=" + time + ", type=" + type + "]";
	}
}

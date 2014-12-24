package com.example.locationtest.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	@SuppressLint("SimpleDateFormat")
	public static String convertTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = format.format(new Date(time));
		return currentTime;
	}

	@SuppressLint("SimpleDateFormat")
	public static String getImageName(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = format.format(new Date(time));
		return "IMG_" + currentTime + ".jpg";
	}
	
	@SuppressLint("SimpleDateFormat")
	public static String getAudioName(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = format.format(new Date(time));
		return "Audio_" + currentTime + ".amr";
	}
}

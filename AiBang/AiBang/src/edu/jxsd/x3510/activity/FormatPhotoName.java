package edu.jxsd.x3510.activity;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 设置照片的格式为IMG_yyyyMMdd_HHmmss
 */
@SuppressLint("SimpleDateFormat")
public class FormatPhotoName {
	private static final String pattern = "yyyyMMdd_HHmmss";// 照片名字格式
	private SimpleDateFormat dataFormat;
	private String imgName = null;

	public FormatPhotoName(){
		super();
	}
	public String getPhotoName(Date date) {
		dataFormat = new SimpleDateFormat(pattern);
		imgName = "IMG_"+dataFormat.format(date)+".jpg";//照片名字
		return imgName;
	}
}

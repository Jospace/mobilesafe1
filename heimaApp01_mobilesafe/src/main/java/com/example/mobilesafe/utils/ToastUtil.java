package com.example.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	/**
	 * 显示传入的内容
	 * @param cxt 上下文对象
	 * @param msg 显示的内容
	 */
	public static void show(Context cxt, String msg) {
		Toast.makeText(cxt, msg, Toast.LENGTH_SHORT).show();
		
	}

}

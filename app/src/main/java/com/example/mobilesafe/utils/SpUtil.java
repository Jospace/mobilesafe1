package com.example.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtil {

	private static SharedPreferences sp;

	/**
	 * @param ctx 上下文对象
	 * @param key 要存储的节点名称
	 * @param value 存储的节点名称的值
	 */
	public static void putBoolean(Context ctx, String key, boolean value) {
		//存储文件节点名称，读写方式
		if(sp == null) {
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		//得到sp编辑器,存入数据，提交
		sp.edit().putBoolean(key, value).commit();
		
	}
	/**
	 * @param ctx 上下文
	 * @param key 节点名称
	 * @param defValue 读取不到节点值时的默认值
	 * @return 节点值或默认值
	 */
	public static boolean getBoolean(Context ctx, String key, boolean defValue) {
		//存储文件节点名称，读写方式
		if(sp == null) {
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		//得到sp中对应节点的值
		return sp.getBoolean(key, defValue);
	}
	/**
	 * @param ctx 上下文对象
	 * @param key 要存储的节点名称
	 * @param value 存储的节点名称的值
	 */
	public static void putString(Context ctx, String key, String value) {
		//存储文件节点名称，读写方式
		if(sp == null) {
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		//得到sp编辑器,存入数据，提交
		sp.edit().putString(key, value).commit();

	}
	/**
	 * @param ctx 上下文
	 * @param key 节点名称
	 * @param defValue 读取不到节点值时的默认值
	 * @return 节点值或默认值
	 */
	public static String getString(Context ctx, String key, String defValue) {
		//存储文件节点名称，读写方式
		if(sp == null) {
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		//得到sp中对应节点的值
		return sp.getString(key, defValue);
	}

	/**
	 * 删除sp中对应节点
	 * @param ctx 上下文对象
	 * @param key 节点
	 */
	public static void remove(Context ctx, String key) {
		//存储文件节点名称，读写方式
		if(sp == null) {
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		//得到sp编辑器,删除对应节点，提交
		sp.edit().remove(key).commit();
	}
}

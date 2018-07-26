package com.example.mobilesafe.engine;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Joe on 2018/7/24.
 */

public class AddressDao {

	public static String path = "data/data/com.example.mobilesafe/files/address.db";
	private static String mAddress;

	/**
	 * 查询数据库，获取手机号归属地
	 *
	 * @param phone 要查询的手机号码
	 */
	public static String getAddress(String phone) {
		mAddress = "未知号码";
		//使用正则表达式匹配手机号码
		String regularExpression = "^1[3-8]\\d{9}";
		boolean matches = phone.matches(regularExpression);
		//获取一个数据库连接
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		//如果匹配为手机号码则进行如下查询
		if (matches) {
			phone = phone.substring(0, 7);
			//数据库查询
			Cursor cursor = db.query("data1", new String[]{"outkey"}, "id= ?", new String[]{phone}, null, null, null);
			//查到即可
			if (cursor.moveToNext()) {
				//查询得到表1的id
				String outkey = cursor.getString(0);
				//将表1的id作为表2的外键查询表2
				Cursor indexCursor = db.query("data2", new String[]{"location"}, "id=?", new String[]{outkey}, null, null, null);
				if (indexCursor.moveToNext()) {
					mAddress = indexCursor.getString(0);
				}
			}
		} else {
			int length = phone.length();
			switch (length) {
				case 3:
					mAddress = "报警电话";
					break;
				case 4:
					mAddress = "模拟器";
					break;
				case 5:
					mAddress = "服务电话";
					break;
				case 7:
					mAddress = "本地电话";
					break;
				case 8:
					mAddress = "本地电话";
					break;
				case 11:
					//(3+8) 区号+座机号码(外地),查询data2
					String area = phone.substring(1, 3);
					Cursor cursor = db.query("data2", new String[]{"location"}, "area = ?", new String[]{area}, null, null, null);
					if (cursor.moveToNext()) {
						mAddress = cursor.getString(0);
					} else {
						mAddress = "未知号码";
					}
					break;
				case 12:
					//(4+8) 区号(0791(江西南昌))+座机号码(外地),查询data2
					String area1 = phone.substring(1, 4);
					Cursor cursor1 = db.query("data2", new String[]{"location"}, "area = ?", new String[]{area1}, null, null, null);
					if (cursor1.moveToNext()) {
						mAddress = cursor1.getString(0);
					} else {
						mAddress = "未知号码";
					}
					break;
			}
		}
		return mAddress;
	}
}

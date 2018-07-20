package com.example.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtil {

	/**
	 * 输入流转换为字符串
	 * 
	 * @param is 输入流对象
	 * @return 输入流转换成的字符串 返回null则为异常
	 */
	public static String stream2String(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 读取流操作
		byte[] buffer = new byte[1024];
		int temp = -1;
		try {
			while ((temp = is.read(buffer)) != -1) {
				baos.write(buffer, 0, temp);
			}
			//返回读取数据
			return baos.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				//关闭流
				is.close();
				baos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		// TODO Auto-generated method stub

	}

}

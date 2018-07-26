package com.example.mobilesafe.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * 判断服务是否正在运行
 * Created by Joe on 2018/7/25.
 */

public class ServiceUtil {

	private static ActivityManager mActivityManager;

	/**
	 * 判断服务是否正在运行的服务
	 *
	 * @param ctx         上下文对象
	 * @param serviceName 服务名
	 * @return true 服务正在运行 false 没有运行
	 */
	public static boolean isRunning(Context ctx, String serviceName) {
		//获取activity管理者
		mActivityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> runningServices = mActivityManager.getRunningServices(100);
		//遍历获得的服务list
		for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
			String className = runningServiceInfo.service.getClassName();
			//判断是否含有要查询的服务，如果有，返回true
			if(serviceName.equals(className)){
				return true;
			}
		}
		return false;
	}
}

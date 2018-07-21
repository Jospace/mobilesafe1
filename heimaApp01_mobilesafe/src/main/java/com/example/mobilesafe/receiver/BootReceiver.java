package com.example.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.mobilesafe.activity.ConstantValue;
import com.example.mobilesafe.utils.SpUtil;

/**
 * Created by Joe on 2018/7/21.
 */

public class BootReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("TAG","接收到了开机广播。。。。");
		//获取sp中存储的sim卡序列号
		String spSimSerialNum = SpUtil.getString(context, ConstantValue.SIM_NUM, "");
		//获取新的sim卡序列号
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String serialNumber = tm.getSimSerialNumber()+"222";
		//比较两个序列号是否一致
		if(!spSimSerialNum.equals(serialNumber)){
			//发送短信到安全号码
			SmsManager smsManager = SmsManager.getDefault();
			//获取sp中的安全号码
			String phoneNum = SpUtil.getString(context, ConstantValue.PHONE_NUM, "");
			//发送短信
			smsManager.sendTextMessage(phoneNum,null,"sim changed!",null,null);
		}
	}
}

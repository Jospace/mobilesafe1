package com.example.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.mobilesafe.R;
import com.example.mobilesafe.activity.ConstantValue;
import com.example.mobilesafe.service.LocationService;
import com.example.mobilesafe.utils.SpUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by Joe on 2018/7/21.
 */

public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		//判断是否开启了防盗保护
		boolean open_security = SpUtil.getBoolean(context, ConstantValue.OPEN_SECURITY, false);
		if (open_security) {
			//获取短信内容，需要添加接收短信的权限
			Object[] objects = (Object[]) intent.getExtras().get("pdus");
			//遍历短信过程
			for (Object object : objects) {
				SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);
				//获取短信对象的基本信息
				String originatingAddress = sms.getOriginatingAddress();//发送短信的号码
				String messageBody = sms.getMessageBody();//短信内容
				//判断是否包含播放音乐的关键字
				if (messageBody.contains("#*alarm*#")) {
					//播放音乐
					MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
					//设置无限循环
					mediaPlayer.setLooping(true);
					//播放音乐
					mediaPlayer.start();
				}

				//进行GPS追踪
				if(messageBody.contains("#*location*#")){
					//开启获取经纬度的服务
					context.startService(new Intent(context,LocationService.class));
				}
			}

		}
	}
}

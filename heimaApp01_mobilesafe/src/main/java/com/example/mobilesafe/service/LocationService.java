package com.example.mobilesafe.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;

import com.example.mobilesafe.activity.ConstantValue;
import com.example.mobilesafe.utils.SpUtil;

import java.util.List;

/**
 * Created by Joe on 2018/7/21.
 */

public class LocationService extends Service {
	@Override
	public void onCreate() {
		super.onCreate();
		//获取服务管理者
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		//以最优的方式获取经纬度坐标
		Criteria criteria = new Criteria();
		//允许花费
		criteria.setCostAllowed(true);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String bestProvider = locationManager.getBestProvider(criteria, true);
		//在一定时间间隔，移动一定距离后，获取经纬度
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		locationManager.requestLocationUpdates(bestProvider, 0, 0, new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				//获取纬度
				double latitude = location.getLatitude();
				//获取经度
				double longitude = location.getLongitude();
				//将经纬度以短信发送到安全号码上
				SmsManager smsManager = SmsManager.getDefault();
				String phoneNum = SpUtil.getString(getApplicationContext(), ConstantValue.PHONE_NUM, "");
				smsManager.sendTextMessage(phoneNum, null,
						"latitude:" + latitude + ",longitude:" + longitude, null, null);
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {

			}

			@Override
			public void onProviderEnabled(String provider) {

			}

			@Override
			public void onProviderDisabled(String provider) {

			}
		});
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}

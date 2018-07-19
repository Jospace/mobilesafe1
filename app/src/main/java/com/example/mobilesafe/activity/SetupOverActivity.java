package com.example.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.SpUtil;

/**
 * Created by Joe on 2018/7/18.
 */

public class SetupOverActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//获取是否设置完成的标志
		boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER, false);
		//判断是否设置完成
		if(setup_over){
			//如果设置完成，停留在设置完成界面
		setContentView(R.layout.activity_setup_over);
		}else {
			//如果没有设置完成，跳转到设置1界面
			Intent intent = new Intent(this, Setup1Activity.class);
			startActivity(intent);
			finish();
		}
	}
}

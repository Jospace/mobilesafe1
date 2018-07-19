package com.example.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.SpUtil;

public class Setup4Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
	}

	public void prePage(View v) {
		Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
		startActivity(intent);
		finish();
	}

	public void nextPage(View v) {
		Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
		startActivity(intent);
		finish();
		//设置完成后，将标志位存储在sp中
		SpUtil.putBoolean(this,ConstantValue.SETUP_OVER,true);
	}
}

package com.example.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.mobilesafe.R;

import static android.content.ContentValues.TAG;

public class Setup1Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
		Log.i(TAG, "onCreate: 测试一下Git的使用");

	}
}

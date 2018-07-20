package com.example.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mobilesafe.R;

import static android.content.ContentValues.TAG;

public class Setup1Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}

	/**
	 * 点击下一页按钮的方法
	 * @param v
	 */
	public  void nextPage(View v){
		Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
		startActivity(intent);
		finish();
		//设置平移动画
		overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
	}
}

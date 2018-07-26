package com.example.mobilesafe.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilesafe.R;
import com.example.mobilesafe.engine.AddressDao;

public class QueryAddressActivity extends Activity {

	private EditText et_activity_query_address_phone;
	private Button btn_activity_query_address;
	private TextView tv_activity_query_result;
	private String mAddress;
	private String phone;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			//控件显示查询结果
			tv_activity_query_result.setText(mAddress);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_address);

		initUI();
	}

	private void initUI() {
		//初始化视图控件
		et_activity_query_address_phone = (EditText) findViewById(R.id.et_activity_query_address_phone);
		btn_activity_query_address = (Button) findViewById(R.id.btn_activity_query_address);
		tv_activity_query_result = (TextView) findViewById(R.id.tv_activity_query_result);

		//注册按钮的点击事件
		btn_activity_query_address.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				phone = et_activity_query_address_phone.getText().toString();
				//判断输入框是否为空
				if (!TextUtils.isEmpty(phone)) {
					query(phone);
				} else {
					//如果为空，输入框抖动
					Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
					et_activity_query_address_phone.startAnimation(animation);
					//手机震动，获取手机震动器，并添加权限
					Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					vibrator.vibrate(1000);
				}
			}
		});
		//实时查询（监听输入框文本变化）
		et_activity_query_address_phone.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				phone = et_activity_query_address_phone.getText().toString();
				query(phone);
			}
		});
	}

	/**
	 * 耗时操作
	 * 获取电话号码归属地
	 *
	 * @param phone 查询的电话号码
	 */
	private void query(final String phone) {
		new Thread() {
			@Override
			public void run() {
				mAddress = AddressDao.getAddress(phone);
				//使用消息机制，查询到后，界面显示结果
				mHandler.sendEmptyMessage(0);
			}
		}.start();
	}
}

package com.example.mobilesafe.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
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
				String phone = et_activity_query_address_phone.getText().toString();
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

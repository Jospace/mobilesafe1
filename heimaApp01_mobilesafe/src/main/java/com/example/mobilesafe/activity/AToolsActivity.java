package com.example.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mobilesafe.R;

public class AToolsActivity extends Activity {

	private TextView tv_query_phone_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atool);
		//归属地查询方法
		initPhoneAddress();
	}


	/**
	 * 归属地查询方法
	 */
	private void initPhoneAddress() {
		tv_query_phone_address = (TextView) findViewById(R.id.tv_activity_atool_qp);
		tv_query_phone_address.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(),QueryAddressActivity.class));
			}
		});
	}
}

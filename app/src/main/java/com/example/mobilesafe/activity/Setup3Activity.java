package com.example.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobilesafe.R;
import com.example.mobilesafe.activity.Setup2Activity;

public class Setup3Activity extends Activity {

	private EditText et_phone_num;
	private Button btn_select_contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		//初始化UI
		initUI();
	}


	private void initUI() {
		//显示电话号码的输入框
		et_phone_num = (EditText) findViewById(R.id.et_phone_num);
		//点击选择联系人的按钮
		btn_select_contact = (Button) findViewById(R.id.btn_select_contact);
		//创建按钮的点击监听
		btn_select_contact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ContactSelectActivity.class);
				startActivityForResult(intent, 0);
			}
		});
	}

	public void nextPage(View v) {
		Intent intent = new Intent(getApplicationContext(), Setup4Activity.class);
		startActivity(intent);
		finish();
	}

	public void prePage(View v) {
		Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
		startActivity(intent);
		finish();
	}
}

package com.example.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobilesafe.R;
import com.example.mobilesafe.activity.Setup2Activity;
import com.example.mobilesafe.utils.SpUtil;

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
		//电话号码回显
		String phone_num = SpUtil.getString(getApplicationContext(), ConstantValue.PHONE_NUM, "");
		et_phone_num.setText(phone_num);
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//判断回传数据是否为空
		if(data!=null){
			String phone = data.getStringExtra("phone");
			//过滤特殊字符，除去空格
			phone = phone.replace("-","").replace(" ","").trim();
			//在输入框显示
			et_phone_num.setText(phone);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void nextPage(View v) {
		//判断输入框是否为空
		String phone_num = et_phone_num.getText().toString();
		if(!TextUtils.isEmpty(phone_num)){
			//存储输入框的电话号码到sp中
			SpUtil.putString(getApplicationContext(),ConstantValue.PHONE_NUM,phone_num);
			Intent intent = new Intent(getApplicationContext(), Setup4Activity.class);
			startActivity(intent);
			finish();
			//设置平移动画
			overridePendingTransition(R.anim.next_in_anim,R.anim.next_out_anim);
		}else {
			Toast.makeText(getApplicationContext(), "请输入电话号码！", Toast.LENGTH_SHORT).show();
		}
	}

	public void prePage(View v) {
		Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
		startActivity(intent);
		finish();
		//设置平移动画
		overridePendingTransition(R.anim.pre_in_anim,R.anim.pre_out_anim);
	}
}

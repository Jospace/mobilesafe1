package com.example.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.SpUtil;

public class Setup4Activity extends BaseActivity {

	private CheckBox cb_open_security;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		initUI();
	}

	@Override
	protected void showNextPage() {
		//勾选了手机防盗保护，跳转到设置完成界面
		boolean open_security = SpUtil.getBoolean(getApplicationContext(), ConstantValue.OPEN_SECURITY, false);
		if (open_security) {
			Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
			startActivity(intent);
			finish();
			//将设置完成的key更新为true
			SpUtil.putBoolean(getApplicationContext(), ConstantValue.SETUP_OVER, true);
			//设置平移动画
			overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
		} else {
			Toast.makeText(getApplicationContext(), "请开启手机防盗保护！", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void showPrePage() {
		Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
		startActivity(intent);
		finish();
		//设置平移动画
		overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
	}

	/**
	 * 初始化视图对象
	 */
	private void initUI() {
		cb_open_security = (CheckBox) findViewById(R.id.cb_open_security);
		//判断check_box的选中状态，回显
		boolean open_security = SpUtil.getBoolean(getApplicationContext(), ConstantValue.OPEN_SECURITY, false);
		cb_open_security.setChecked(open_security);
		if (open_security) {
			cb_open_security.setText("安全防护已开启");
		} else {
			cb_open_security.setText("安全防护已关闭");
		}
		//添加cb_box的点击监听事件
		cb_open_security.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					cb_open_security.setText("安全防护已开启");
				} else {
					cb_open_security.setText("安全防护已关闭");
				}
				//将点击后的选中状态存储到sp中
				SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_SECURITY, isChecked);
			}
		});
	}
}

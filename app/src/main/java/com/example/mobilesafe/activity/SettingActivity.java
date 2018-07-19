package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.SpUtil;
import com.example.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingActivity extends Activity {

	private boolean checked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initUpdate();
	}

	private void initUpdate() {
		final SettingItemView siv_setting = (SettingItemView) findViewById(R.id.siv_setting);
		CheckBox cb_box = (CheckBox) siv_setting.findViewById(R.id.cb_box);
		//获取sp中的siv_setting视图选中状态
		boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
		siv_setting.setCheck(open_update);
		
		//添加cb_box视图对象的状态切换监听
		cb_box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				siv_setting.setCheck(isChecked);
				SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE,isChecked);
				Log.i("TAG", "点击了按钮"+isChecked);
			}
		});
		
		// 添加视图对象的点击监听
		siv_setting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 获取之前的选中状态
				checked = siv_setting.isChecked();
				// 如果之前是选中的，点击之后变为未选中；如果之前未选中，点击之后变为选中
				siv_setting.setCheck(!checked);
				//将点击之后的视图状态记录到sp中
				SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE, !checked);
			}
		});

	}
}

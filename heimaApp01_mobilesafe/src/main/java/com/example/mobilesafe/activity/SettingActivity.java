package com.example.mobilesafe.activity;

import com.example.mobilesafe.R;
import com.example.mobilesafe.service.AddressService;
import com.example.mobilesafe.utils.ServiceUtil;
import com.example.mobilesafe.utils.SpUtil;
import com.example.mobilesafe.view.SettingClickView;
import com.example.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SettingActivity extends Activity {

	private boolean checked;
	private String[] mToastStyleDesc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initUpdate();
		//初始化归属地
		initAddress();
		//初始化自定义toast
		initToastStyle();
	}

	/**
	 * 初始化自定义的Toast
	 */
	private void initToastStyle() {
		//初始化自定义控件
		SettingClickView scv_toast_style = (SettingClickView) findViewById(R.id.scv_toast_style);
		//设置自定义控件标题内容
		scv_toast_style.setTitle("设置归属地显示风格");
		//创建描述文字的String类型数组
		mToastStyleDesc = new String[]{"透明", "橙色", "蓝色", "灰色", "绿色"};
		//在sp中获取描述
		int toastStyle = SpUtil.getInt(getApplicationContext(), ConstantValue.TOAST_STYLE, 0);
		//根据获取的顺序，设置描述内容
		scv_toast_style.setDesc(mToastStyleDesc[toastStyle]);
		//监听点击事件，弹出对话框

	}

	/**
	 * 初始化归属地
	 */
	private void initAddress() {
		final SettingItemView siv_address = (SettingItemView) findViewById(R.id.siv_address);
		//查询服务是否在运行，如果在运行，设置为已开启
		boolean isRunning = ServiceUtil.isRunning(this,"com.example.mobilesafe.service.AddressService");
		siv_address.setCheck(isRunning);
		//点击过程中，状态的切换过程
		siv_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//获取之前的选中状态
				boolean isChecked = siv_address.isChecked();
				//设置与之前状态相反的状态
				siv_address.setCheck(!isChecked);
				//根据是否开启的状态，判断是否开启服务
				if (!isChecked) {
					//开启服务，管理吐司
					startService(new Intent(getApplicationContext(), AddressService.class));
				} else {
					//停止服务，不需要显示吐司
					stopService(new Intent(getApplicationContext(), AddressService.class));
				}
			}
		});


	}

	private void initUpdate() {
		final SettingItemView siv_setting = (SettingItemView) findViewById(R.id.siv_setting);
		CheckBox cb_box = (CheckBox) siv_setting.findViewById(R.id.cb_box);
		//获取sp中的siv_setting视图选中状态
		boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
		siv_setting.setCheck(open_update);

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

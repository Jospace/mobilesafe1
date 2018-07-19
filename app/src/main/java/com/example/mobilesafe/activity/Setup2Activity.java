package com.example.mobilesafe.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.SpUtil;
import com.example.mobilesafe.view.SettingItemView;


public class Setup2Activity extends Activity {

	private SettingItemView siv_bind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);

		//初始化控件
		initUI();
	}

	private void initUI() {
		siv_bind = (SettingItemView) findViewById(R.id.siv_bind);
		//回显，读取已有的绑定状态，用作显示，sp中是否存储了
		final String sim_num = SpUtil.getString(getApplicationContext(), ConstantValue.SIM_NUM, "");
		//如果为空，则没有绑定
		if (TextUtils.isEmpty(sim_num)) {
			siv_bind.setCheck(false);
		} else {
			siv_bind.setCheck(true);
		}
		//添加siv_bind的点击监听
		siv_bind.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//判断之前的绑定状态
				boolean isCheck = siv_bind.isChecked();
				//点击之后，将之前状态取反
				siv_bind.setCheck(!isCheck);
				//如果更改为选中，则需要存储sim卡序列号到sp中
				if(!isCheck){
					//获取sim卡序列号
					TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
					String simSerialNumber = manager.getSimSerialNumber();
					SpUtil.putString(getApplicationContext(),ConstantValue.SIM_NUM,simSerialNumber);
				}else {
					//否则删除sp中的SIM_NUM节点
					SpUtil.remove(getApplicationContext(),ConstantValue.SIM_NUM);
				}
			}
		});
	}

	public void nextPage(View v) {
		//判断是否绑定，如果绑定了，才跳转到下一页
		String sim_num = SpUtil.getString(getApplicationContext(), ConstantValue.SIM_NUM, "");
		if(!TextUtils.isEmpty(sim_num)){
		Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
		startActivity(intent);
		finish();
		}else {
			Toast.makeText(this, "请先绑定SIM卡", Toast.LENGTH_SHORT).show();
		}
	}

	public void prePage(View v) {
		Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
		startActivity(intent);
		finish();
	}
}

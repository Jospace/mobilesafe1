package com.example.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.SpUtil;

/**
 * Created by Joe on 2018/7/18.
 */

public class SetupOverActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//获取是否设置完成的标志
		boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER, false);
		//判断是否设置完成
		if(setup_over){
			//如果设置完成，停留在设置完成界面
		setContentView(R.layout.activity_setup_over);
		initUI();
		}else {
			//如果没有设置完成，跳转到设置1界面
			Intent intent = new Intent(this, Setup1Activity.class);
			startActivity(intent);
			finish();
		}
	}

	private void initUI() {
		//从sp中获取数据，显示安全号码
		TextView tv_safe_num = (TextView) findViewById(R.id.tv_safe_num);
		Button btn_reset = (Button) findViewById(R.id.btn_reset);
		String phone_num = SpUtil.getString(getApplicationContext(), ConstantValue.PHONE_NUM, "");
		tv_safe_num.setText(phone_num);
		//添加重新进入设置向导按钮的点击事件
		btn_reset.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//重新进入设置界面1
				Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}

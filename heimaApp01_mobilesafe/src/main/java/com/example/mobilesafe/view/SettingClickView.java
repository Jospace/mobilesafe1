package com.example.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe.R;

public class SettingClickView extends RelativeLayout {

	private static final String NAME_SPACE = "http://schemas.android.com/apk/res/com.example.mobilesafe";
	private CheckBox cb_box;
	private TextView tv_desc;
	private TextView tv_title;

	public SettingClickView(Context context) {
		this(context, null);
	}

	public SettingClickView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SettingClickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//xml-->view  将设置界面的一个条目转换成view对象，直接添加到当前SettingClickView对应的view中
		View.inflate(context, R.layout.setting_click_view, this);
		//自定义组合控件中的标题描述
		tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_desc = (TextView) this.findViewById(R.id.tv_desc);
	}

	/**
	 * 设置标题内容
	 * @param title 标题
	 */
	public void setTitle(String title){
		tv_title.setText(title);
	}

	/**
	 * 设置描述内容
	 * @param desc 标题
	 */
	public void setDesc(String desc) {
		tv_desc.setText(desc);
	}


}

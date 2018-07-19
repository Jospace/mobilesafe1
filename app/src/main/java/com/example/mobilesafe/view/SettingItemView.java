package com.example.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe.R;

public class SettingItemView extends RelativeLayout {

	private static final String NAME_SPACE = "http://schemas.android.com/apk/res/com.example.mobilesafe";
	private CheckBox cb_box;
	private TextView tv_desc;
	private String destitle;
	private String deson;
	private String desoff;

	public SettingItemView(Context context) {
		this(context, null);
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//xml-->view  将设置界面的一个条目转换成view对象，直接添加到当前SettingItemView对应的view中
		View.inflate(context, R.layout.setting_item_view, this);
		//自定义组合控件中的标题描述
		TextView tv_title = (TextView) this.findViewById(R.id.tv_title);
		tv_desc = (TextView) this.findViewById(R.id.tv_desc);
		cb_box = (CheckBox) this.findViewById(R.id.cb_box);
		//获取自定义以及原生属性的操作，AttributeSet attrs对象中获取
		initAttrs(attrs);
		//获取布局文件中定义的字符串，赋值给自定义组合控件的标题
		tv_title.setText(destitle);
	}

	/**
	 * 返回属性集合中自定义的属性
	 * @param attrs 构造方法中维护好的属性集合
	 */
	private void initAttrs(AttributeSet attrs) {
		Log.i("TAG", "调用了initAttrs方法");
		destitle = attrs.getAttributeValue(NAME_SPACE, "destitle");
		deson = attrs.getAttributeValue(NAME_SPACE, "deson");
		desoff = attrs.getAttributeValue(NAME_SPACE, "desoff");
		Log.i("TAG", "destitle" + destitle);
		Log.i("TAG", "desoff" + desoff);
		Log.i("TAG", "deson" + deson);
	}

	/**
	 * 判断SettingItemView对象是否被选中
	 *
	 * @return 与checkBox的选中状态保持一致
	 */
	public boolean isChecked() {
		Log.i("TAG", "调用了isCheck方法");
		return cb_box.isChecked();
	}

	/**
	 * 更新checkBox的选中状态，并更新tv_desc的文本
	 *
	 * @param isCheck
	 */
	public void setCheck(boolean isCheck) {
		Log.i("TAG", "调用了setCheck方法");
		cb_box.setChecked(isCheck);
		if (isCheck) {
			tv_desc.setText("自动更新已开启");
		} else {
			tv_desc.setText("自动更新已关闭");
		}
	}

}

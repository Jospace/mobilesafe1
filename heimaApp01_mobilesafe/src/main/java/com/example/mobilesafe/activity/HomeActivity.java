package com.example.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilesafe.R;
import com.example.mobilesafe.bean.MenuInfo;
import com.example.mobilesafe.utils.Md5Util;
import com.example.mobilesafe.utils.SpUtil;

import java.util.ArrayList;

public class HomeActivity extends Activity {

	private GridView gv_home;
	private ArrayList<MenuInfo> mData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// 初始化UI
		initUI();
		// 初始化数据
		initData();
	}

	private void initData() {
		//准备适配器数据
		mData = new ArrayList<MenuInfo>();
		mData.add(new MenuInfo(R.drawable.home_safe, "手机防盗"));
		mData.add(new MenuInfo(R.drawable.home_callmsgsafe, "通讯卫士"));
		mData.add(new MenuInfo(R.drawable.home_apps, "软件管理"));
		mData.add(new MenuInfo(R.drawable.home_taskmanager, "进程管理"));
		mData.add(new MenuInfo(R.drawable.home_netmanager, "流量统计"));
		mData.add(new MenuInfo(R.drawable.home_trojan, "手机杀毒"));
		mData.add(new MenuInfo(R.drawable.home_sysoptimize, "缓存清理"));
		mData.add(new MenuInfo(R.drawable.home_tools, "高级工具"));
		mData.add(new MenuInfo(R.drawable.home_settings, "设置中心"));
		//设置适配器
		gv_home.setAdapter(new MyAdapter());
		//设置点击GridView中Item的监听
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
					case 0://手机防盗
						//弹出设置密码或输入密码的对话框
						showDialog();
						break;
					case 7://高级工具
						//弹出高级工具页面
						startActivity(new Intent(getApplicationContext(),AToolsActivity.class));
						break;
					case 8://设置中心
						Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
						startActivity(intent);
						break;

					default:
						break;
				}

			}
		});
	}

	/**
	 * 弹出设置密码或确认密码的对话框
	 */
	private void showDialog() {
		//判断本地是否存有密码（sp)
		String psd = SpUtil.getString(this, ConstantValue.MOBILE_SAFE_PSD, "");
		if (TextUtils.isEmpty(psd)) {
			//如果没有，弹出设置密码对话框
			showSetPsdDialog();
		} else {
			//如果有，弹出确认密码对话框
			showConfirmPsdDialot();

		}
	}

	/**
	 * 弹出确认密码对话框
	 */
	private void showConfirmPsdDialot() {
		//因为要显示自定义样式的对话框，所以要使用dialog.setView(View)方法，view为自定义对话框布局
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		final View view = View.inflate(this, R.layout.dialog_confirm_psd, null);
		dialog.setView(view);
		//显示自定义样式的对话框
		dialog.show();
		//初始化按钮
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		//添加按钮的点击监听
		btn_submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//初始化输入框控件
				EditText et_con_psd = (EditText) view.findViewById(R.id.et_con_psd);
				//判断输入框密码是否为空
				String conPsd = et_con_psd.getText().toString().toString();
				if (!TextUtils.isEmpty(conPsd)) {
					//如果输入框不为空，判断确认密码是否正确
					String getPsd = SpUtil.getString(HomeActivity.this, ConstantValue.MOBILE_SAFE_PSD, "");
					if (getPsd.equals(Md5Util.encoder(conPsd))) {
						//密码一致，进入手机防盗界面
						Intent intent = new Intent(HomeActivity.this, SetupOverActivity.class);
						startActivity(intent);
						//进入页面之后取消对话框显示
						dialog.dismiss();
					} else {
						Toast.makeText(HomeActivity.this, "密码输入错误！", Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(HomeActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//添加取消按钮的点击监听
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	/**
	 * 弹出设置密码对话框
	 */
	private void showSetPsdDialog() {
		//因为要显示自定义样式的对话框，所以要使用dialog.setView(View)方法，view为自定义对话框布局
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog dialog = builder.create();
		final View view = View.inflate(this, R.layout.dialog_set_psd, null);
		dialog.setView(view);
		//显示自定义样式的对话框
		dialog.show();
		//初始化按钮
		Button btn_submit = (Button) view.findViewById(R.id.btn_submit);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		//添加按钮的点击监听
		btn_submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//初始化输入框控件
				EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
				EditText et_con_psd = (EditText) view.findViewById(R.id.et_con_psd);
				//判断输入框密码是否合法
				String setPsd = et_set_psd.getText().toString();
				String conPsd = et_con_psd.getText().toString().toString();
				if (!TextUtils.isEmpty(setPsd) && !TextUtils.isEmpty(conPsd)) {
					//如果输入框都不为空，判断两次密码输入是否一致
					if (setPsd.equals(conPsd)) {
						//密码一致，进入手机防盗界面
						Intent intent = new Intent(HomeActivity.this, SetupOverActivity.class);
						startActivity(intent);
						//进入页面之后取消对话框显示
						dialog.dismiss();
						SpUtil.putString(HomeActivity.this, ConstantValue.MOBILE_SAFE_PSD, Md5Util.encoder(setPsd));
					} else {
						Toast.makeText(HomeActivity.this, "密码输入不一致！", Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(HomeActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
				}
			}
		});
		//添加取消按钮的点击监听
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	private void initUI() {
		gv_home = (GridView) findViewById(R.id.gv_home);

	}

	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//创建或得到当前行的一个ViewHolder对象，将convertView的子视图保存到ViewHolder对象中
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(HomeActivity.this, R.layout.gv_item, null);
				holder = new ViewHolder();
				holder.iv_item = (ImageView) convertView.findViewById(R.id.iv_item);
				holder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
				//将holder保存到convertView
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			//给ViewHolder中的视图设置数据
			MenuInfo menuInfo = mData.get(position);
			holder.iv_item.setImageResource(menuInfo.getDrawableId());
			holder.tv_item.setText(menuInfo.getTitleStr());
			return convertView;
		}

		class ViewHolder {
			private ImageView iv_item;
			private TextView tv_item;
		}
	}
}

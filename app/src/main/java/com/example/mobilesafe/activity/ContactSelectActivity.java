package com.example.mobilesafe.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mobilesafe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactSelectActivity extends Activity {

	private ArrayList<Map<String,String>> contactList = new ArrayList<Map<String,String>>();
	private ContactAdapter mAdapter;
	private ListView lv_contact;

	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			mAdapter = new ContactAdapter();
			lv_contact.setAdapter(mAdapter);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_select);
		initUI();
		//初始化数据
		initData();
	}

	class ContactAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return contactList.size();
		}

		@Override
		public Object getItem(int position) {
			return contactList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ContactViewHolder viewHolder = null;
			if(convertView == null){
				viewHolder = new ContactViewHolder();
				convertView = View.inflate(ContactSelectActivity.this,R.layout.lv_contact_item,null);
				viewHolder.tv_name = convertView.findViewById(R.id.tv_name);
				viewHolder.tv_phone = convertView.findViewById(R.id.tv_phone);
				//将viewHolder对象保存到convertView中
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ContactViewHolder) convertView.getTag();
			}
			//得到联系人List中的数据
			Map<String, String> map = contactList.get(position);
			//给viewHolder对象中的视图设置数据
			viewHolder.tv_name.setText(map.get("contactName"));
			viewHolder.tv_phone.setText(map.get("contactPhone"));
			return convertView;
		}

		class ContactViewHolder{
			private TextView tv_name;
			private TextView tv_phone;
		}
	}

	private void initUI() {
		lv_contact = (ListView) findViewById(R.id.lv_contact);
	}


	/**
	 * 初始化联系人数据
	 */
	private void initData() {
		//查询联系人耗时,启动分线程
		new Thread(){
			@Override
			public void run() {
				//获取内容解析器
				ContentResolver resolver = getContentResolver();
				//组织查询字段
				String [] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
						ContactsContract.CommonDataKinds.Phone.NUMBER};
				Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,
						null, null, null);
				while (cursor.moveToNext()){
					HashMap<String, String> contact = new HashMap<>();
					String contactName = cursor.getString(0);
					String contactPhone = cursor.getString(1);
					contact.put("contactName",contactName);
					contact.put("contactPhone",contactPhone);
					contactList.add(contact);
				}
				mHandler.sendEmptyMessage(0);
			}
		}.start();
	}
}

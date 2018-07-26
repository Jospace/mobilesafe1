package com.example.mobilesafe.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilesafe.R;
import com.example.mobilesafe.engine.AddressDao;

/**
 * Created by Joe on 2018/7/25.
 */

public class AddressService extends Service {

	private TelephonyManager mTm;
	private MyPhoneStateListner myPhoneStateListner;
	private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
	private View mViewToast;
	private WindowManager mWM;
	private TextView tv_toast;
	private String mAddress;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			tv_toast.setText(mAddress);
		}
	};

	@Override
	public void onCreate() {
		//监听电话的状态
		mTm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		//需要传入一个电话状态监听器对象
		myPhoneStateListner = new MyPhoneStateListner();
		mTm.listen(myPhoneStateListner, PhoneStateListener.LISTEN_CALL_STATE);
		//获取窗体对象
		mWM = (WindowManager) getSystemService(WINDOW_SERVICE);
		super.onCreate();
	}

	class MyPhoneStateListner extends PhoneStateListener {
		//手动重写，电话状态发生改变会触发的方法
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:
					//空闲状态，没有任何活动（移除吐司）
					if(mWM!= null && mViewToast!=null){
						mWM.removeView(mViewToast);
					}
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:
					//摘机状态，至少有一个电话活动。该活动或是拨打，或是通话

					break;
				case TelephonyManager.CALL_STATE_RINGING:
					//响铃状态(展示吐司)
					showToast(incomingNumber);

					break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
	}

	/**
	 * 显示自定义样式的吐司
	 *
	 * @param incomingNumber 来电号码
	 */
	private void showToast(String incomingNumber) {
		final WindowManager.LayoutParams params = mParams;
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
		params.width = WindowManager.LayoutParams.WRAP_CONTENT;
		params.format = PixelFormat.TRANSLUCENT;
		//在响铃的时候显示吐司，和电话类型一致
		params.type = WindowManager.LayoutParams.TYPE_PHONE;
		params.setTitle("Toast");
		params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;  默认能够被触摸
		//指定吐司的所在位置
		params.gravity = Gravity.LEFT + Gravity.TOP;
		//将吐司布局转换为对象
		mViewToast = View.inflate(getApplicationContext(), R.layout.toast_view, null);
		tv_toast = mViewToast.findViewById(R.id.tv_toast);
		//在窗体上挂载一个view(需要配权限)
		mWM.addView(mViewToast, params);

		//获取到电话号码后，需要做来电号码查询
		query(incomingNumber);
	}

	/**
	 * 查询来电号码归属地
	 * @param incomingNumber
	 */
	private void query(final String incomingNumber) {
		new Thread(){
			@Override
			public void run() {
				mAddress = AddressDao.getAddress(incomingNumber);
				mHandler.sendEmptyMessage(0);
			}
		}.start();
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		//关闭服务的时候，取消电话状态的监听
		//判断非空
		if (mTm != null && myPhoneStateListner != null) {
			//myPhoneStateListner --> 开启服务的时候监听电话的对象
			mTm.listen(myPhoneStateListner, PhoneStateListener.LISTEN_NONE);
		}
		super.onDestroy();
	}
}

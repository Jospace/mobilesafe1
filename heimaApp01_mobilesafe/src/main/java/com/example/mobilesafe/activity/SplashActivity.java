package com.example.mobilesafe.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.mobilesafe.R;
import com.example.mobilesafe.utils.SpUtil;
import com.example.mobilesafe.utils.StreamUtil;
import com.example.mobilesafe.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class SplashActivity extends Activity {

	// 提示用户更新的状态码
	protected static final int UPDATE_VERSION = 100;
	// 进入主界面状态码
	protected static final int ENTER_HOME = 101;
	// JSON解析异常状态码
	protected static final int JSON_ERROR = 102;
	// URL异常状态码
	protected static final int URL_ERROR = 103;
	// IO异常状态码
	protected static final int IO_ERROR = 104;

	private TextView tv_version_name;
	private int mLocalVersionCode;
	private String mVersionDes;
	private String mDownloadUrl;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case UPDATE_VERSION:
				// 弹出对话框，提示用户更新
				showUpdateDialog();
				break;
			case ENTER_HOME:
				// 进入主界面,开启新的activity,结束导航界面
				enterHome();
				finish();
				break;
			case JSON_ERROR:
				// JSON解析异常
				ToastUtil.show(SplashActivity.this, "JSON解析异常");
				enterHome();
				finish();
				break;
			case URL_ERROR:
				// URL异常
				ToastUtil.show(SplashActivity.this, "URL异常");
				enterHome();
				finish();
				break;
			case IO_ERROR:
				// IO异常
				ToastUtil.show(SplashActivity.this, "IO异常");
				enterHome();
				finish();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉当前Activity头Title
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		// 初始化UI
		initUI();
		// 初始化数据
		initData();
		//初始化数据库
		initDB();
	}

	/**
	 * 初始化数据库
	 */
	private void initDB() {
		//初始化归属地查询数据库
		initAddressDB("address.db");
	}

	/**
	 * 初始化归属地查询数据库
	 */
	private void initAddressDB(String dbName) {
		InputStream is = null;
		FileOutputStream fos = null;
		//将数据库文件加载到应用的File文件夹下的file文件中
		File filesDir = getFilesDir();
		File file = new File(filesDir, dbName);
		//如果file存在，直接返回
		if(file.exists()){
			return;
		}
		//如果不存在，加载assets文件夹中的数据
		try {
			is = getAssets().open(dbName);
			//将读取的内容写到指定的文件中去
			fos = new FileOutputStream(file);
			//指定读取长度
			byte[] bytes = new byte[1024];
			int len = -1;
			while ((len = is.read(bytes))!=-1){
				fos.write(bytes,0,len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(is!=null && fos != null){
				try {
					is.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 弹出提示更新的对话框
	 */
	protected void showUpdateDialog() {
		Builder builder = new AlertDialog.Builder(this);
		// 设置对话框图标
		builder.setIcon(R.drawable.ic_launcher);
		// 设置标题
		builder.setTitle("版本更新");
		// 设置内容
		builder.setMessage(mVersionDes);
		// 设置积极按钮
		builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 根据下载地址，下载apk
				// 判断SD卡是否可用
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					// 1.获取sd卡路径
					String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
							+ "mobilesafe.apk";
					// 2.发送请求，获取apk，并放到指定位置
					HttpUtils httpUtils = new HttpUtils();
					httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
						@Override
						public void onSuccess(ResponseInfo<File> responseInfo) {
							// 下载成功
							Log.i("TAG", "下载成功");
							File file = responseInfo.result;
							//提示用户安装apk
							installApk(file);
						}

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// 下载失败
							Log.i("TAG", "下载失败");
						}

						// 刚刚开始下载的方法
						@Override
						public void onStart() {
							super.onStart();
							Log.i("TAG", "开始下载");
						}

						// 下载过程中的方法,total:下载的apk总大小；current:当前下载进度;isUploading:是否正在下载
						public void onLoading(long total, long current, boolean isUploading) {
							Log.i("TAG", "下载中···");
							Log.i("TAG", "Total："+total);
							Log.i("TAG", "Current："+current);
						};
					});
				}
			}
		});
		//设置消极按钮
		builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 进入主界面
				enterHome();
				finish();
			}
		});
		//点击取消事件监听(点击回退按钮)进入主界面
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				enterHome();
				finish();
			}
		});
		builder.show();
	}
	/**
	 * 安装apk
	 * @param file 要安装的apk的file文件
	 */
	private void installApk(File file) {
		//设置隐示意图action
		Intent intent = new Intent("android.intent.action.VIEW");
		//设置category
		intent.addCategory("android.intent.category.DEFAULT");
		//设置数据和类型
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		//开启意图，使用带返回结果的意图开始方式，确保在开启了安装apk的activity后，用户点击取消，能进入主界面，而不会卡在导航界面
		//startActivity(intent);
		startActivityForResult(intent, 0);
	}

	//结束开启的activity后，调用的方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//进入主界面
		enterHome();
		finish();
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 进入主界面
	 */
	protected void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

	/**
	 * 获取数据方法
	 */
	private void initData() {
		// 1.应用版本名称
		tv_version_name.setText("版本名称：" + getVersionName());
		// 检测（本地版本号和服务器版本号比对）是否有更新，如果有更新，提示用户下载
		// 2.获取本地版本号
		mLocalVersionCode = getVersionCode();
		// 3.获取服务器版本号（客户端发请求，服务端给响应，（json,xml））
		// http://www.xx.com/update.json?key=value 返回200请求成功，以流的方式将数据读取下来
		// json中包含的内容：
		/*
		 * 更新版本的版本名称 新版本的描述信息 服务器版本号 apk下载地址
		 */
		if(SpUtil.getBoolean(this,ConstantValue.OPEN_UPDATE,false)){
			//如果返回true，则检查版本更新
			checkVersion();
		}else {
			mHandler.sendEmptyMessageDelayed(ENTER_HOME,4000);
		}
	}

	/**
	 * 检测版本号
	 */
	private void checkVersion() {
		// 分线程执行
		new Thread() {

			public void run() {
				// 发送请求，获取服务器端数据。访问地址：http://192.168.56.1/update.json
				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();
				try {
					// 1.开启一个连接
					URL url = new URL("http://192.168.56.1/update.json");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					// 2.设置连接参数,请求超时
					connection.setConnectTimeout(2000);
					// 读取数据超时
					connection.setReadTimeout(2000);
					// 3.获取响应码
					if (connection.getResponseCode() == 200) {
						// 4.获取输入流
						InputStream is = connection.getInputStream();
						// 5.将输入流转换为字符串
						String json = StreamUtil.stream2String(is);
						Log.i("TAG", "获取的服务器数据为：" + json);
						// 6.解析json数据
						try {
							JSONObject jsonObject = new JSONObject(json);
							String versionName = jsonObject.getString("versionName");
							mVersionDes = jsonObject.getString("versionDes");
							String versionCode = jsonObject.getString("versionCode");
							mDownloadUrl = jsonObject.getString("downloadUrl");
							// 7.比对版本号，若客户端版本号小于服务端版本号，则提示用户更新；否则直接进入主界面。
							// 由于此为分线程，更新UI需在主线程，需使用消息机制
							if (mLocalVersionCode < Integer.parseInt(versionCode)) {
								// 提示用户更新
								msg.what = UPDATE_VERSION;
							} else {
								// 进入主界面
								msg.what = ENTER_HOME;
							}
						} catch (JSONException e) {
							e.printStackTrace();
							msg.what = JSON_ERROR;
						}
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					msg.what = URL_ERROR;
				} catch (IOException e) {
					e.printStackTrace();
					msg.what = IO_ERROR;
				} finally {
					// 计算请求数据的时间，如果小于4秒，则睡眠到4秒，防止导航界面显示速度过快
					long endTime = System.currentTimeMillis();
					if (endTime - startTime < 3000) {
						try {
							Thread.sleep(3000 - (endTime - startTime));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					// 发送消息
					mHandler.sendMessage(msg);
				}
			};
		}.start();

	}

	/**
	 * 获取本地版本号
	 *
	 * @return 非0则代表获取成功
	 */
	private int getVersionCode() {
		// 获取使用包管理者
		PackageManager pm = getPackageManager();
		// 获取指定包名的基本信息（版本号和版本名称） 传0代表获取基本信息
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			// 获取版本名称
			return packageInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	/**
	 * 获取版本名称：清单文件中
	 *
	 * @return 应用版本名称 返回null代表出现异常。
	 */
	private String getVersionName() {
		// 获取使用包管理者
		PackageManager pm = getPackageManager();
		// 获取指定包名的基本信息（版本号和版本名称） 传0代表获取基本信息
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			// 获取版本名称
			return packageInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 初始化UI方法
	 */
	private void initUI() {
		tv_version_name = (TextView) findViewById(R.id.tv_version_name);

	}
}

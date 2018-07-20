package com.example.mobilesafe.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Joe on 2018/7/20.
 */

public abstract class BaseActivity extends Activity {

	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//2.创建手势管理的对象，用作管理onTouchEvent(event)传递过来的手势动作
		gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				//监听手势的移动
				if(e1.getX()-e2.getX()>0){
					//调用子类的下一页方法，抽象方法
					showNextPage();
				}
				if (e1.getX() - e2.getX() < 0) {
					//调用子类的下一页方法，抽象方法
					showPrePage();
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}

	//下一页方法
	protected abstract void showNextPage();

	//上一页方法
	protected abstract void showPrePage();

	//点击下一页按钮的时候,根据子类的showNextPage方法做相应跳转
	public void nextPage(View v){
		showNextPage();
	}

	//点击下一页按钮的时候,根据子类的showNextPage方法做相应跳转
	public void prePage(View v){
		showPrePage();
	}

	//1.监听屏幕上响应的事件类型
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//调用手势管理器的onTouchEvent()方法
		gestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
}

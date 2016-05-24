/**
 * MyApplication.java [V 1..0.0]
 * classes : com.hb56.DriverReservation.android.app.MyApplication
 * zhangyx Create at 2014-11-26 下午4:04:30
 */
package com.huidao.lgw.mygesturekey;

import android.app.Application;

import com.huidao.lgw.mygesturekey.view.LockPatternUtils;

/***
 * 
 *
 *
 */
public class MyApplication extends Application {

	private LockPatternUtils mLockPatternUtils;// 手势锁

	private String userName;
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */

	@Override
	public void onCreate() {
		super.onCreate();
		// 手势锁
		mLockPatternUtils = new LockPatternUtils(this);
	}

	public LockPatternUtils getLockPatternUtils() {
		return mLockPatternUtils;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}

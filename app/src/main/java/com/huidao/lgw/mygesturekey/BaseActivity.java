package com.huidao.lgw.mygesturekey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.huidao.lgw.mygesturekey.util.ToastHelper;


/**
 * Created by Administrator on 2016/5/21.
 */
public class BaseActivity extends AppCompatActivity {
    private MyApplication app;// 程序进程


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        app = (MyApplication) getApplication();

    }

    public void showToast(String message) {
        ToastHelper.show(this, message);
    }

    /**
     * @return the app
     */
    public MyApplication getApp() {
        return app;
    }


}

package com.huidao.lgw.mygesturekey;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

//import android.support.v7.widget.ViewUtils;
import android.text.TextPaint;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;


import com.huidao.lgw.mygesturekey.gesture.GuideGesturePasswordActivity;
import com.huidao.lgw.mygesturekey.gesture.UnlockGesturePasswordActivity;
import com.huidao.lgw.mygesturekey.util.AnimationUtil;
import com.huidao.lgw.mygesturekey.view.ClearEditText;

public class LoginActivity extends BaseActivity {

      @ViewInject(value = R.id.cet_userNo)
    private ClearEditText userName;
       @ViewInject(R.id.cet_userPwd)
    private ClearEditText userPwd;
      @ViewInject(R.id.tv_login)
    private TextView loginBtn;

    public LoginActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        userName = (ClearEditText) findViewById(R.id.cet_userNo);
//        userPwd = (ClearEditText) findViewById(R.id.cet_userPwd);
//        loginBtn = (TextView) findViewById(R.id.tv_login);

        // 初始化IOC注解
       ViewUtils.inject(this);
        initView();
    }


    /**
     *
     */
    private void initView() {
        // TODO Auto-generated method stub
        //addTextViewTextChangeListener();

        if (null != getApp().getLockPatternUtils()) {
            if (getApp().getLockPatternUtils().savedPatternExists()) {
                startActivity(new Intent(LoginActivity.this,UnlockGesturePasswordActivity.class));
                this.finish();
                return;
            }
        }

        initImgs();

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                doLoginClickExecute();
            }
        });
    }

    // 初始化 图片和Logo字体
    @SuppressWarnings("deprecation")
    private void initImgs() {
        Drawable mIconPerson = getResources().getDrawable(
                // 加载用户名 输入框的图标
                R.mipmap.edit_user_icon);
        mIconPerson.setBounds(5, 1, 60, 50);
        Drawable mIconLock = getResources().getDrawable(
                // 加载 密码 输入框的图标
                R.mipmap.edit_pwd_icon);
        mIconLock.setBounds(5, 1, 60, 50);

        userName.setCompoundDrawables(mIconPerson, null, null, null);
        userPwd.setCompoundDrawables(mIconLock, null, null, null);
        TextView topText = (TextView) findViewById(R.id.topname);
        topText.setTextColor(Color.BLACK);
        topText.setTextSize(26.0f);
        //topText.setTypeface(Typeface.MONOSPACE, Typeface.BOLD_ITALIC);
        // 使用TextPaint的仿“粗体”设置setFakeBoldText为true。目前还无法支持仿“斜体”方法
        // Typeface.BOLD_ITALIC
        TextPaint tp = topText.getPaint();

        tp.setFakeBoldText(true);


    }

    private String userNo;
    private String userPwdT;

    private void doLoginClickExecute() {
        userNo = userName.getText().toString();
        userPwdT = userPwd.getText().toString();
        if (TextUtils.isEmpty(userNo)) {
            showToast("用户名不能为空");
            userName.setFocusable(true);
            userName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(userPwdT)) {
            showToast("密码不能为空");
            userPwd.setFocusable(true);
            userPwd.requestFocus();
            return;
        }

        getApp().setUserName(userNo);

        startActivity(new Intent(this, GuideGesturePasswordActivity.class));
        AnimationUtil.finishActivityAnimation(LoginActivity.this);

        // 请求网络
        //new MyAsyncTaskHttp().execute(userNo);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);

                startActivity(intent);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


}

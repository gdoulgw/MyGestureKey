package com.huidao.lgw.mygesturekey.gesture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.huidao.lgw.mygesturekey.MyApplication;
import com.huidao.lgw.mygesturekey.R;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.ViewUtils;

public class GuideGesturePasswordActivity extends AppCompatActivity {
//    @ViewInject(R.id.rootView_guide)



    @ViewInject(R.id.gesturepwd_guide_btn)
    private Button btn_gesturepwd_guide;    //gesturepwd_guide_btn;

    private MyApplication app;
    private RelativeLayout rootView_guide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_gesture_password);


        ViewUtils.inject(this);
        app=(MyApplication) getApplication();
        rootView_guide = (RelativeLayout) findViewById(R.id.rootView_guide);
        btn_gesturepwd_guide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                toCreateGesturePwd();
            }
        });

    }

    // 去创建 手势密码
    private void toCreateGesturePwd() {
        app.getLockPatternUtils().clearLock();
        Intent intent = new Intent(GuideGesturePasswordActivity.this,
                CreateGesturePasswordActivity.class);
        // 打开新的Activity
        startActivity(intent);
        finish();
    }




}

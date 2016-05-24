package com.huidao.lgw.mygesturekey.gesture;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huidao.lgw.mygesturekey.BaseActivity;
import com.huidao.lgw.mygesturekey.LoginActivity;
import com.huidao.lgw.mygesturekey.MainActivity;
import com.huidao.lgw.mygesturekey.MyApplication;
import com.huidao.lgw.mygesturekey.R;
import com.huidao.lgw.mygesturekey.util.AnimationUtil;
import com.huidao.lgw.mygesturekey.view.LockPatternUtils;
import com.huidao.lgw.mygesturekey.view.LockPatternView;

import java.util.List;

/**
 * 解锁登录
 */

public class UnlockGesturePasswordActivity extends BaseActivity {

    private LockPatternView mLockPatternView;
    private int mFailedPatternAttemptsSinceLastTimeout = 0;
    private CountDownTimer mCountdownTimer = null;

    private Animation mShakeAnim;

    //    @ViewInject(R.id.gesturepwd_unlock_text)
    private TextView mHeadTextView;
    //    @ViewInject(R.id.rootView)
    private LinearLayout rootView;
    //    @ViewInject(R.id.changeUser)
    private Button changeUser;// 切换用户、清除手势锁

    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_gesture_password);

        app=(MyApplication) getApplication();
        mLockPatternView = (LockPatternView) findViewById(R.id.gesturepwd_unlock_lockview);
        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
        mLockPatternView.setTactileFeedbackEnabled(true);
        mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);


        changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清除 手势文件
                app.getLockPatternUtils().clearLock();
                toLoginActivity();
            }
        });

    }

    private void toLoginActivity() {
        startActivity(new Intent(UnlockGesturePasswordActivity.this,
                LoginActivity.class));
        AnimationUtil
                .finishActivityAnimation(UnlockGesturePasswordActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 从未创建过手势时，开始创建---没有记住密码
        if (!app.getLockPatternUtils().savedPatternExists()) {
            toLoginActivity();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountdownTimer != null)
            mCountdownTimer.cancel();
    }


    private Runnable mClearPatternRunnable = new Runnable() {
        public void run() {
            mLockPatternView.clearPattern();
        }
    };


    protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {
        @Override
        public void onPatternStart() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
            patternInProgress();
        }

        @Override
        public void onPatternCleared() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
        }

        @Override
        public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

        }

        private void patternInProgress() {
        }

        @Override
        public void onPatternDetected(List<LockPatternView.Cell> pattern) {
            if (pattern == null)
                return;
            if (app.getLockPatternUtils()
                    .checkPattern(pattern)) {// 解锁成功
                mLockPatternView
                        .setDisplayMode(LockPatternView.DisplayMode.Correct);

                // 解锁成功返回需要用户信息的页面----
                loginSuccessToMainAcrtivity() ;
            } else {// 解锁失败-----重新登录
                mLockPatternView
                        .setDisplayMode(LockPatternView.DisplayMode.Wrong);

                if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {

                    mFailedPatternAttemptsSinceLastTimeout++;
                    int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
                            - mFailedPatternAttemptsSinceLastTimeout;
                    if (retry > 0) {
                        changeUser.setVisibility(View.VISIBLE);
                        if (retry == 0)
                            showToast(UnlockGesturePasswordActivity.this
                                    .getString(R.string.toastlock));
                        mHeadTextView.setText("密码错误，还可以再输入" + retry + "次");
                        mHeadTextView.setTextColor(Color.RED);
                        mHeadTextView.startAnimation(mShakeAnim);
                    } else {
                        // 打开新的Activity
                        // 清除 手势文件
                        app.getLockPatternUtils().clearLock();
                        toLoginActivity();
                    }
                } else {
                    showToast("输入长度不够，请重试");
                }
                mLockPatternView.clearPattern();
            }
        }
    };


    // 登录成功
    private void loginSuccessToMainAcrtivity() {
        startActivity(new Intent(UnlockGesturePasswordActivity.this,
                MainActivity.class));
        AnimationUtil
                .finishActivityAnimation(UnlockGesturePasswordActivity.this);
    }


    Runnable attemptLockout = new Runnable() {

        @Override
        public void run() {
            mLockPatternView.clearPattern();
            mLockPatternView.setEnabled(false);
            mCountdownTimer = new CountDownTimer(
                    LockPatternUtils.FAILED_ATTEMPT_TIMEOUT_MS + 1, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    int secondsRemaining = (int) (millisUntilFinished / 1000) - 1;
                    if (secondsRemaining > 0) {
                        mHeadTextView.setText(secondsRemaining + " 秒后重试");
                    } else {
                        mHeadTextView
                                .setText(UnlockGesturePasswordActivity.this
                                        .getString(R.string.gesture_drawPwd));
                        mHeadTextView.setTextColor(Color.WHITE);
                    }

                }

                @Override
                public void onFinish() {
                    mLockPatternView.setEnabled(true);
                    mFailedPatternAttemptsSinceLastTimeout = 0;
                }
            }.start();
        }
    };

}

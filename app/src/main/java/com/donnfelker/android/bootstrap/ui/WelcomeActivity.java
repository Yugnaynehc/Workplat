package com.donnfelker.android.bootstrap.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.donnfelker.android.bootstrap.R;

/**
 * Created by Feather on 14-7-22.
 */
public class WelcomeActivity extends Activity {

    private static final int GOTO_MAIN_ACTIVITY = 0;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case GOTO_MAIN_ACTIVITY:
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome_activity);
        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 1300); // 1.3秒跳转
    }

}

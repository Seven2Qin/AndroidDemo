package com.seven.launchmode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.seven.annotation.Router;

@Router(path = "MainActivity", module = "LaunchMode")
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_go).setOnClickListener(this);

        ThreadLocal<Boolean> booleanThreadLocal = new ThreadLocal();
        booleanThreadLocal.set(false);

        Log.e("MainActivity",""+booleanThreadLocal.get());
    }

    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
//        intent.setClassName("com.seven.ad","com.seven.ad.TestActivity");
        intent.setClass(this, ActivityA.class);
        startActivity(intent);

    }
}

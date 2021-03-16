package com.seven.launchmode;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.seven.annotation.Router;

@Router(path = "MainActivity", module = "LaunchMode")
public class LaunchModeActivity extends BaseActivity {

    private final static String TAG = LaunchModeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_mode);
        findViewById(R.id.btn_go).setOnClickListener(this);

        ThreadLocal<Boolean> booleanThreadLocal = new ThreadLocal();
        booleanThreadLocal.set(false);

        Log.e("MainActivity", "" + booleanThreadLocal.get());
    }

    @SuppressLint("HandlerLeak")
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        Toast.makeText(LaunchModeActivity.this, "onPause：====", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        Toast.makeText(LaunchModeActivity.this, "" + hasFocus + " ， " + LaunchModeActivity.this.isTaskRoot(), Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        Toast.makeText(LaunchModeActivity.this, "onClick," + LaunchModeActivity.this.getWindow().getCurrentFocus(), Toast.LENGTH_LONG).show();


//        AlertDialog.Builder builder = new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("最普通dialog")
//                .setMessage("我是最简单的dialog").setPositiveButton("确定（积极）", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //ToDo: 你想做的事情
//                        WindowManager windowManager = LaunchModeActivity.this.getWindowManager();
//
//
//                        Toast.makeText(LaunchModeActivity.this, "确定按钮", Toast.LENGTH_LONG).show();
//                    }
//                }).setNegativeButton("取消（消极）", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        //ToDo: 你想做的事情
//                        Toast.makeText(LaunchModeActivity.this, "关闭按钮," + LaunchModeActivity.this.getWindow().getDecorView().getVisibility(), Toast.LENGTH_LONG).show();
//                        dialogInterface.dismiss();
//                    }
//                });
//        builder.create().show();

        Intent intent = new Intent();
//        intent.setClassName("com.seven.ad","com.seven.ad.TestActivity");
        intent.setClass(this, ActivityD.class);
        startActivity(intent);

    }
}

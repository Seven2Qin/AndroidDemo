package com.seven.windowdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_demo);
//        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
//                        1);
//            }else {
                Toast.makeText(MainActivity.this,"toot",Toast.LENGTH_LONG).show();
                Button floatingButton = new Button(MainActivity.this);
                floatingButton.setText("button");
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        0, 0,
                        PixelFormat.TRANSPARENT
                );
                // flag 设置 Window 属性
                layoutParams.flags= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                // type 设置 Window 类别（层级）
                layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
                layoutParams.gravity = Gravity.CENTER;
                WindowManager windowManager = getWindowManager();
                windowManager.addView(floatingButton, layoutParams);
            }
//        }
//    }
}

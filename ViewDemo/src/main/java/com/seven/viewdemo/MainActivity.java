package com.seven.viewdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private CustomButton mButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(new CustomView(this));
        mButton = findViewById(R.id.btn);
        mButton.setOnClickListener(this);
        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG, "onTouch: ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG, "onTouch: ACTION_MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG, "onTouch: ACTION_UP");
                        break;
                }
                return false;
            }
        });


        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Log.e(TAG,"density:"+dm.density);
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "点击了按钮");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"Activity->onTouchEvent"+event.getAction());
        return super.onTouchEvent(event);
    }
}

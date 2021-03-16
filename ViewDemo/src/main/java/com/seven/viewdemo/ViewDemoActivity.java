package com.seven.viewdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class ViewDemoActivity extends AppCompatActivity implements View.OnClickListener, HyperLinkTextView.HyperLinkCallback {

    private static final String TAG = "ViewDemoActivity";
    private CustomButton mButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_demo);
        setContentView(new CustomView(this));
//        mButton = findViewById(R.id.btn_touch);
//        mButton.setOnClickListener(this);
//        mButton.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.e(TAG, "onTouch: ACTION_DOWN");
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        Log.e(TAG, "onTouch: ACTION_MOVE");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.e(TAG, "onTouch: ACTION_UP");
//                        break;
//                }
//                return false;
//            }
//        });
//
//
//        DisplayMetrics dm = this.getResources().getDisplayMetrics();
//        int width = dm.widthPixels;
//        int height = dm.heightPixels;
//        Log.e(TAG, "density:" + dm.density);
//
//        HyperLinkTextView textView = findViewById(R.id.tv_link);
//        textView.setHyperLinkCallback(this, null);
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "点击了按钮");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "Activity->onTouchEvent" + event.getAction());
        return super.onTouchEvent(event);
    }

    @Override
    public void onItemClick(View v, int position, HyperLinkTextView.LinkText linkText) {
        Toast.makeText(this, position + " , " + linkText.getContent(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "Activity dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }
}

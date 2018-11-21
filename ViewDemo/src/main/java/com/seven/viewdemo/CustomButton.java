package com.seven.viewdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class CustomButton extends View {
    private static final String TAG = "CustomButton";

    int x,y;
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMySize(200, widthMeasureSpec);
        int height = getMySize(200, heightMeasureSpec);

        if (width < height) {
            height = width;
        } else {
            width = height;
        }

        setMeasuredDimension(width, height);
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                Log.e(TAG,"mySize-->UNSPECIFIED:"+mySize);
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size
                //我们将大小取最大值,你也可以取其他值
                mySize = size;
                Log.e(TAG,"mySize-->AT_MOST:"+mySize);
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                Log.e(TAG,"mySize-->EXACTLY:"+mySize);
                break;
            }
        }
        return mySize;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onTouchEvent: ACTION_UP");
                break;
        }

        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean intercept = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int) event.getX();
                y = (int) event.getY();
                Log.e(TAG,"11111111111--->x:"+x+" , y="+y);
                super.dispatchTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG,"22222222222--->x:"+(int)event.getX()+" , y="+(int)event.getY());
                int deltaX = (int)event.getX() - x;
                int deltaY = (int)event.getY() - y;
                intercept = Math.abs(deltaY) - Math.abs(deltaX) > 0;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return intercept;
    }
}

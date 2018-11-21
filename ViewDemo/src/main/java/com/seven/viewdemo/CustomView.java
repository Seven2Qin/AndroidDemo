package com.seven.viewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {
    private Paint paint;

    private float cx = 100, cy = 100;

    public CustomView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.rgb(255, 0, 0));
        paint.setTextSize(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(cx, cy, 100, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("CustomView->DOWN", event.getX() + " , " + event.getY());
                cx = event.getX();
                cy = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                Log.i("CustomView->UP", event.getX() + " , " + event.getY());
                cx = event.getX();
                cy = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("CustomView_MOVE", event.getX() + " , " + event.getY());
                cx = event.getX();
                cy = event.getY();
                break;
        }

        invalidate();

        return true;
    }
}

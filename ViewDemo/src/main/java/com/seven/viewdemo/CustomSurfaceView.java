package com.seven.viewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Paint paint;

    private float cx = 100, cy = 100;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    //子线程绘制标记
    private volatile boolean isDrawing;

    public CustomSurfaceView(Context context) {
        super(context);
        init();
    }

    private void init() {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
//        setFocusableInTouchMode(true);
//        setKeepScreenOn(true);

        paint = new Paint();
        paint.setColor(Color.rgb(255, 0, 0));
        paint.setTextSize(10);

    }


//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawCircle(cx, cy, 100, paint);
//    }

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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDrawing = true;
        new Thread(this).start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDrawing = false;
        surfaceHolder.removeCallback(this);

    }

    @Override
    public void run() {
        while (isDrawing) {
            draw();
        }
    }

    private void draw() {
        try {
            canvas = surfaceHolder.lockCanvas();
            //执行具体的绘制操作
            canvas.drawCircle(cx, cy, 100, paint);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}

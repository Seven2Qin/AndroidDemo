package com.seven.hook;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by seven.qin on 2018/12/20.
 */
public class BaseApplication extends Application {
    private static final String TAG = "MainActivity";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.i("MainActivity","BaseApplication --> attachBaseContext");
        try {
            HookHelper.attachContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

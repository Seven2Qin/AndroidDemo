package com.seven.hook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by seven.qin on 2019/1/6.
 */
public class BaseActivity extends Activity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        Log.i(TAG, "MainActivity --> attachBaseContext");
        try {
//            HookHelper.attachContext();
            HookHelper.hookIActivityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

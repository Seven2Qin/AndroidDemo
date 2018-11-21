package com.seven.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/**
 * 跳转不到子页面--http://www.voidcn.com/article/p-qophwsjx-bha.html
 */
//1、在宿主的manifest中声明ProxyActivity的intent-filter的时候，只声明了
//<action android:name="com.ryg.dynamicloadhost.VIEW"/>

public class BaseActivity extends Activity {

    private static final String TAG = "Client-BaseActivity";

    public static final String FROM = "extra.from";
    public static final int FROM_EXTERNAL = 0;
    public static final int FROM_INTERNAL = 1;
    public static final String EXTRA_DEX_PATH = "extra.dex.path";
    public static final String EXTRA_CLASS = "extra.class";

    public static final String PROXY_VIEW_ACTION = "com.seven.base.VIEW";
    public static final String DEX_PATH = "/sdcard/pluginApk-debug.apk";

    protected Activity mProxyActivity;
    protected int mFrom = FROM_INTERNAL;

    public void setProxy(Activity proxyActivity) {
        Log.d(TAG, "setProxy: proxyActivity= " + proxyActivity);
        mProxyActivity = proxyActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mFrom = savedInstanceState.getInt(FROM, FROM_INTERNAL);
        }
        if (mFrom == FROM_INTERNAL) {
            super.onCreate(savedInstanceState);
            mProxyActivity = this;
        }
        Log.d(TAG, "onCreate: from= " + mFrom);
    }



    protected void startActivityByProxy(String className) {
        if (mProxyActivity == this) {
            Intent intent = new Intent();
            intent.setClassName(this, className);
            this.startActivity(intent);
        } else {
            //其实这里启动的就是代理ProxyActivity本身，所以在这个activity的注册信息里面要添加这个action
            Intent intent = new Intent(PROXY_VIEW_ACTION);
            //必须和宿主里面的路径一致。
            intent.putExtra(EXTRA_DEX_PATH, DEX_PATH);
            intent.putExtra(EXTRA_CLASS, className);
            //也就是自己启动自己，启动了一个代理activity后，执行加载插件activity的方法
            mProxyActivity.startActivity(intent);
            //代理activity启动模式为标准模式，这样才能实现多个activity实例
        }
    }

    @Override
    public void setContentView(View view) {
        if (mProxyActivity == this) {
            super.setContentView(view);
        } else {
            mProxyActivity.setContentView(view);
        }
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        if (mProxyActivity == this) {
            super.setContentView(view, params);
        } else {
            mProxyActivity.setContentView(view, params);
        }
    }

    @Deprecated
    @Override
    public void setContentView(int layoutResID) {
        if (mProxyActivity == this) {
            super.setContentView(layoutResID);
        } else {
            mProxyActivity.setContentView(layoutResID);
        }
    }

    @Override
    public void addContentView(View view, LayoutParams params) {
        if (mProxyActivity == this) {
            super.addContentView(view, params);
        } else {
            mProxyActivity.addContentView(view, params);
        }
    }

}

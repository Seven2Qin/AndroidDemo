package com.seven.rn.preloader;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.facebook.react.ReactActivity;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.seven.rn.preloader.PreLoadReactDelegate;

import javax.annotation.Nullable;

/**
 * 预加载Activity基类
 * Created by seven.qin on 2018/12/25.
 */
public class PreLoaderReactActivity extends ReactActivity implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {

    public final static String BUNDLE_NAME = "bundle_name";
    public final static String DEFAULT_MAIN_COMPONENT = "YMMRNDemo";
    public final static String DEFAULT_JSBUNDLE = "1.bundle";
    private PreLoadReactDelegate mPreLoadReactDelegate;


    private PreLoadReactDelegate createPreLoadReactDelegate() {
        return new PreLoadReactDelegate(this);
    }

    /**
     * 子类重写，返回RN对应的界面组件名称
     * @return
     */
    protected @Nullable String getMainComponentName() {
        return DEFAULT_MAIN_COMPONENT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreLoadReactDelegate = createPreLoadReactDelegate();
        mPreLoadReactDelegate.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPreLoadReactDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreLoadReactDelegate.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreLoadReactDelegate.onDestroy();
    }

    @Override
    public void onNewIntent(Intent intent) {
        if(!mPreLoadReactDelegate.onNewIntent(intent)) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPreLoadReactDelegate.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mPreLoadReactDelegate.onRNKeyUp(keyCode) || super.onKeyUp(keyCode, event);
    }

    /**
     * 处理权限授权
     * @param permissions
     * @param requestCode
     * @param listener
     */
    @Override
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        mPreLoadReactDelegate.requestPermissions(permissions,requestCode,listener);
    }

    /**
     * 授权结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(final int requestCode, final String[] permissions, final int[] grantResults) {
        mPreLoadReactDelegate.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

}
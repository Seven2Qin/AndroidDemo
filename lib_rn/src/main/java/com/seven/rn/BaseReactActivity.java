package com.seven.rn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

/**
 * Created by seven.qin on 2018/12/25.
 */
public class BaseReactActivity extends Activity implements DefaultHardwareBackBtnHandler {

    public final static String BUNDLE_NAME = "bundle_name";
    private final static int OVERLAY_PERMISSION_REQ_CODE = 1001;
    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //这里不加权限判断 6.0或以上机型会闪退
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }

    }

    /**
     * 初始化
     */
    protected void init() {

        String bundleName = getIntent().getStringExtra(BUNDLE_NAME);

        mReactRootView = new ReactRootView(this);
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
//                .setBundleAssetName(bundleName)
                .setJSBundleFile("/sdcard/"+bundleName)
                .setJSMainModulePath("index")
                .addPackage(new MainReactPackage())
                /**
                 * http://stackoverflow.com/questions/37951246/react-native-cannot-find-development-server-integrating-existing-android-app
                 * 调试模式下,建议直接写成 true 吧,我就因为这个错误,调了两天原因
                 */
//                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setUseDeveloperSupport(true)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();
        mReactRootView.startReactApplication(mReactInstanceManager, "YMMRNDemo", null);

        setContentView(mReactRootView);
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        onHostPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onHostResume();
    }

    private void onHostPause() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostPause();
        }
    }

    private void onHostResume() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onHostResume(this, this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    new AlertDialog.Builder(this).setTitle("提示").setMessage("请打开悬浮窗权限").show();
                }
            }
        }
    }
}
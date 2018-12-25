package com.seven.rn.preloader;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seven.qin on 2018/12/25.
 */

public class ReactNativePreLoader {

    private static final Map<String, ReactRootView> CACHE = new HashMap<>();
    private static ReactInstanceManager mReactInstanceManager;

    /**
     * 初始化ReactRootView，并添加到缓存
     *
     * @param activity
     * @param jsBundle
     */
    public static void preLoad(Activity activity, String jsBundle) {

        if (CACHE.get(PreLoaderReactActivity.DEFAULT_MAIN_COMPONENT) != null) {
            return;
        }
        // 1.创建ReactRootView
        ReactRootView rootView = new ReactRootView(activity);
        mReactInstanceManager = getReactInstanceManager(activity,jsBundle);
        rootView.startReactApplication(
                mReactInstanceManager,
                PreLoaderReactActivity.DEFAULT_MAIN_COMPONENT,
                null);
        // 2.添加到缓存
        CACHE.put(PreLoaderReactActivity.DEFAULT_MAIN_COMPONENT, rootView);
    }

    /**
     * 获取ReactRootView
     *
     * @param componentName
     * @return
     */
    public static ReactRootView getReactRootView(String componentName) {
        return CACHE.get(componentName);
    }

    /**
     * 从当前界面移除 ReactRootView
     *
     * @param component
     */
    public static void deatchView(String component) {
        try {
            ReactRootView rootView = getReactRootView(component);
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        } catch (Throwable e) {
            Log.e("ReactNativePreLoader", e.getMessage());
        }

    }

    public static void setReactInstanceManager(Activity activity, String jsBundle) {
        ReactRootView rootView = ReactNativePreLoader.getReactRootView(PreLoaderReactActivity.DEFAULT_MAIN_COMPONENT);
        if (rootView == null) {
            rootView = new ReactRootView(activity);
            mReactInstanceManager = ReactNativePreLoader.getReactInstanceManager(activity,jsBundle);
            rootView.startReactApplication(
                    mReactInstanceManager,
                    PreLoaderReactActivity.DEFAULT_MAIN_COMPONENT,
                    null);
        }
        if (rootView.getReactInstanceManager() == null) {
            mReactInstanceManager = ReactNativePreLoader.getReactInstanceManager(activity,jsBundle);
            rootView.startReactApplication(mReactInstanceManager, PreLoaderReactActivity.DEFAULT_MAIN_COMPONENT, null);
        }
        activity.setContentView(rootView);

    }

    public static ReactInstanceManager getReactInstanceManager(Activity activity, String jsBundle) {
        //默认jsBundle
        if (TextUtils.isEmpty(jsBundle)) {
            jsBundle = PreLoaderReactActivity.DEFAULT_JSBUNDLE;
        }
        if (mReactInstanceManager == null) {
            mReactInstanceManager = ReactInstanceManager.builder()
                    .setApplication(activity.getApplication())
//                .setBundleAssetName(bundleName)
//                        .setJSBundleFile("/sdcard/"+bundleName)
                    .setJSBundleFile("/sdcard/" + jsBundle)
                    .setJSMainModulePath("index")
                    .addPackage(new MainReactPackage())
//                .setUseDeveloperSupport(BuildConfig.DEBUG)
                    .setUseDeveloperSupport(true)
                    .setInitialLifecycleState(LifecycleState.RESUMED)
                    .build();
        }
        return mReactInstanceManager;
    }
}
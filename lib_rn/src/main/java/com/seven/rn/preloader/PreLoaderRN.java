package com.seven.rn.preloader;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;
import com.seven.rn.utils.PreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seven.qin on 2018/12/25.
 */

public class PreLoaderRN {

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

        //保存当前预加载jsBundle name
        PreferencesUtils.putString(activity,PreLoaderReactActivity.PRELOADER_JSBUNDLE_KEY,jsBundle);

        // 1.创建ReactRootView
        ReactRootView rootView = new ReactRootView(activity);
        mReactInstanceManager = getReactInstanceManager(activity);
        rootView.startReactApplication(mReactInstanceManager, PreLoaderReactActivity.DEFAULT_MAIN_COMPONENT, null);
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
            Log.e("PreLoaderRN", e.getMessage());
        }

    }


    public static ReactInstanceManager getReactInstanceManager(Activity activity) {
        //默认jsBundle
        String jsBundle =  PreferencesUtils.getString(activity,PreLoaderReactActivity.PRELOADER_JSBUNDLE_KEY);
        if (TextUtils.isEmpty(jsBundle)) {
            jsBundle = PreLoaderReactActivity.DEFAULT_JSBUNDLE;
        }
        if (mReactInstanceManager == null) {
            mReactInstanceManager = ReactInstanceManager.builder()
                    .setApplication(activity.getApplication())
//                .setBundleAssetName(bundleName)
//                        .setJSBundleFile("/sdcard/"+bundleName)
                    .setJSBundleFile(PreLoaderReactActivity.JSBUNDLE_PATH + jsBundle)
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
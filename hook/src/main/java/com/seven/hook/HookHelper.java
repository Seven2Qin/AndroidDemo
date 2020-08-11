package com.seven.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by seven.qin on 2018/12/20.
 */
public class HookHelper {

    public static void attachContext() throws Exception{
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        // 拿到原始的 mInstrumentation字段
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

        // 创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

        // 偷梁换柱
        mInstrumentationField.set(currentActivityThread, evilInstrumentation);
    }

    public static void attachActivity(Activity activity) throws Exception{
        // 先获取到当前的Activity对象
//        Class<?> activityClass = activity.getClass();
//        Object activityObj = activity;
////        Method currentActivityThreadMethod = activityClass.getDeclaredMethod("currentActivityThread");
////        currentActivityThreadMethod.setAccessible(true);
////        Object currentActivity= currentActivityThreadMethod.invoke(null);
//
//        // 拿到原始的 mInstrumentation字段
//        Field mInstrumentationField = activityClass.getDeclaredField("mInstrumentation");
//        mInstrumentationField.setAccessible(true);
//        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(activityObj);
//
//        // 创建代理对象
//        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);
//
//        // 偷梁换柱
//        mInstrumentationField.set(activityObj, evilInstrumentation);

        Class<?> k = Activity.class;
        try {
            //通过Activity.class 拿到 mInstrumentation字段
            Field field = k.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            //根据activity内mInstrumentation字段 获取Instrumentation对象
            Instrumentation instrumentation = (Instrumentation)field.get(activity);
            //创建代理对象
            Instrumentation instrumentationProxy = new EvilInstrumentation(instrumentation);
            //进行替换
            field.set(activity,instrumentationProxy);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }
    }

    public static void hookIActivityManager() {
        try {
            Class<?> c = Class.forName("android.app.ActivityManagerNative");

            //修复8.0 hook失败
            Field field =null;
            if (Build.VERSION.SDK_INT >= 26) {
                Class<?> activityManager = Class.forName("android.app.ActivityManager");
                field = activityManager.getDeclaredField("IActivityManagerSingleton");
            }else{
                Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
                field = activityManagerNativeClass.getDeclaredField("gDefault");
            }

            field.setAccessible(true);

            //返回ActivityManagerNative对象
            Object amn = field.get(null);

            Class<?> k = Class.forName("android.util.Singleton");

            Field field1 = k.getDeclaredField("mInstance");

            field1.setAccessible(true);

            //拿到ActivityManagerProxy对象 代理ActivityManagerNative对象的子类ActivityManagerService
            //为什么不是IActivityManager对象
            //因为在gDefault对象的 实现方法 onCreate()方法中 asInterface(b)返回的是  return new ActivityManagerProxy(obj) 具体可以看源码

            Object iActivityManager = field1.get(amn);

            IamInvocationHandler iamInvocationHandler = new IamInvocationHandler(iActivityManager);

            Object object = Proxy.newProxyInstance(iamInvocationHandler.getClass().getClassLoader(),iActivityManager.getClass().getInterfaces(),iamInvocationHandler);

            field1.set(amn,object);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class IamInvocationHandler implements InvocationHandler {

        Object iamObject;

        public IamInvocationHandler(Object iamObject) {
            this.iamObject = iamObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            Log.e(TAG, method.getName());
            if ("startActivity".equals(method.getName())) {
                Log.e("hook", "要开始启动了 啦啦啦啦啦啦  ");
            }
            return method.invoke(iamObject, args);
        }
    }


}

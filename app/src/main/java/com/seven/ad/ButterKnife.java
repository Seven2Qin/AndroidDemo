package com.seven.ad;

import android.app.Activity;
import android.util.Log;

//import com.seven.annotation.BindView;

import java.lang.reflect.Field;

public class ButterKnife {
    private final static String TAG = "ButterKnife";
    public static void inject(Activity activity) {
        try {
            // 获取类变量
            Class contextClass = Class.forName(activity.getClass().getCanonicalName());
            // 遍历类中所有Field
            for (Field field : contextClass.getDeclaredFields()) {
                // 如果包含注解
//                if (field.isAnnotationPresent(BindView.class)) {
//                    Log.d(TAG, field.getName() + " has annotation");
//
//                    // 得到注解值
//                    int rId = field.getAnnotation(BindView.class).value();
//                    String type = field.getType().toString();
//                    if (type.endsWith("TextView")) {
//                        // 设置field可访问，并将通过set方法赋值view
//                        field.setAccessible(true);
//                        field.set(activity, activity.findViewById(rId));
//                    }
//                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } /*catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/
    }

}

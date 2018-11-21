package com.seven.ad;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.seven.library.DLProxyActivity;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

//import com.seven.annotation.BindView;


public class MainActivity extends Activity implements View.OnClickListener {

    private final static String TAG = "MainActivity";
//        @BindView(R.id.btn_launchMode)
    TextView mLaunchMode;
    //    @BindView(R.id.btn_javaDemo)
    TextView mJavaDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
//        Resources resources = getBundleResource(getApplicationContext());
//        int layout = resources.getIdentifier("activity_main", "layout", "com.seven.base");
//        View view = LayoutInflater.from(this).inflate(layout, null);
//        setContentView(view);
        setContentView(R.layout.activity_main);

//        ClassLoader classLoader = getClassLoader();
//        if (classLoader != null) {
//            Log.i(TAG, "[onCreate] classLoader " + " : " + classLoader.toString());
//            while (classLoader.getParent() != null) {
//                classLoader = classLoader.getParent();
//                Log.i(TAG, "[onCreate] classLoader getParent " + " : " + classLoader.toString());
//            }
//        }

        //isGrantExternalRW();

//        ImageView img = findViewById(R.id.img);
//        mLaunchMode = findViewById(R.id.btn_launchMode);
//        Drawable drawable = resources.getDrawable(resources.getIdentifier("ic_launcher_round", "mipmap", "com.seven.base"));
////        Drawable drawable = resources.getDrawable(0x7f0a0001);
//        img.setImageDrawable(drawable);
//        mLaunchMode.setBackgroundColor(resources.getColor(resources.getIdentifier("colorAccent","color","com.seven.base")));
//


//        ButterKnife.inject(this);
        mLaunchMode = findViewById(R.id.btn_launchMode);
////        mJavaDemo = findViewById(R.id.btn_javaDemo);
//
        mLaunchMode.setOnClickListener(this);
//        mJavaDemo.setOnClickListener(this);
    }

    /**
     * 获取储存权限
     *
     * @return
     */

    public void isGrantExternalRW() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && MainActivity.this.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            MainActivity.this.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

        } else {
//            testAsyncTask();
            handleDexClassLoader();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_launchMode:
                Intent intent = new Intent(this, DLProxyActivity.class);
                intent.putExtra(DLProxyActivity.EXTRA_DEX_PATH, "/sdcard/pluginApk-debug.apk");
                startActivity(intent);
                break;
            case R.id.btn_javaDemo:
                Toast.makeText(MainActivity.this, "javaDemo", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void handleDexClassLoader() {
        File optimizedDexOutputPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "base-debug.apk");// 外部路径
        Log.i(TAG, "path--->jar" + optimizedDexOutputPath.getPath());
        File dexOutputDir = this.getDir("dex", 0);// 无法直接从外部路径加载.dex文件，需要指定APP内部路径作为缓存目录（.dex文件会被解压到此目录）
        Log.i(TAG, "path--->dex" + dexOutputDir.getPath());
        DexClassLoader dexClassLoader = new DexClassLoader(optimizedDexOutputPath.getAbsolutePath(), dexOutputDir.getAbsolutePath(), null, getClassLoader());

        Log.i(TAG, "path--->optimizedDexOutputPath ：" + optimizedDexOutputPath.getAbsolutePath());
        Log.i(TAG, "path--->dexOutputDir ：" + dexOutputDir.getAbsolutePath());
        Class clazz = null;
        try {
            clazz = dexClassLoader.loadClass("com.seven.base.DateUtil");
            // 遍历类里所有方法
            Method[] methods = clazz.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Log.e(TAG, methods[i].toString());
            }
            Method method = clazz.getDeclaredMethod("dateConvert", String.class);// 获取方法
            method.setAccessible(true);// 把方法设为public，让外部可以调用
            Log.i(TAG, "string--->" + method.getName());
            String string = (String) method.invoke(null, new Object[]{"2018"});// 调用方法并获取返回值
            Log.i(TAG, "string--->" + string);
            Toast.makeText(this, string, Toast.LENGTH_LONG).show();
        } catch (Exception exception) {
            // Handle exception gracefully here.
            exception.printStackTrace();
            Log.e(TAG, exception.getMessage());
        }

    }

    private AssetManager createAssetManager() {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(
                    assetManager, "/sdcard/base-debug.apk");
            return assetManager;
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }

    private Resources getBundleResource(Context context) {
        AssetManager assetManager = createAssetManager();
        Resources superRes = context.getResources();
        return new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
    }

    @Override
    protected void attachBaseContext(Context context) {
        Log.i(TAG,"attachBaseContext");
//        replaceContextResources(context);
        super.attachBaseContext(context);
    }

    /**
     * 使用反射的方式，使用Bundle的Resource对象，替换Context的mResources对象
     *
     * @param context
     */
    public void replaceContextResources(Context context) {
        try {
            Field field = context.getClass().getDeclaredField("mResources");
            field.setAccessible(true);
            field.set(context, getBundleResource(context));
            System.out.println("debug:repalceResources succ");
        } catch (Exception e) {
            System.out.println("debug:repalceResources error");
            e.printStackTrace();
        }
    }

}

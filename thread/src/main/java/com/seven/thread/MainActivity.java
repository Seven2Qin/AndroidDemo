package com.seven.thread;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.seven.thread.net.UrlConnManager;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isGrantExternalRW();

//        startHandlerThread();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                UrlConnManager.useHttpUrlConnectionPost("https://address-api.1000.com/api/region");
//            }
//        }).start();

    }

    private void handleDexClassLoader() {
        File optimizedDexOutputPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "util.jar");// 外部路径
        Log.i(TAG,"path--->jar"+optimizedDexOutputPath.getPath());
        File dexOutputDir = this.getDir("dex", 0);// 无法直接从外部路径加载.dex文件，需要指定APP内部路径作为缓存目录（.dex文件会被解压到此目录）
        Log.i(TAG,"path--->dex"+dexOutputDir.getPath());
        DexClassLoader dexClassLoader = new DexClassLoader(optimizedDexOutputPath.getAbsolutePath(), dexOutputDir.getAbsolutePath(), null, getClassLoader());

        Log.i(TAG,"path--->optimizedDexOutputPath ："+optimizedDexOutputPath.getAbsolutePath());
        Log.i(TAG,"path--->dexOutputDir ："+dexOutputDir.getAbsolutePath());
        Class clazz = null;
        try{
            clazz=dexClassLoader.loadClass("com.seven.compiler.DateUtil");
            // 遍历类里所有方法
            Method[]methods=clazz.getDeclaredMethods();
            for(int i=0;i<methods.length;i++){
                Log.e(TAG,methods[i].toString());
            }
            Method method=clazz.getDeclaredMethod("dateConvert",String.class);// 获取方法
            method.setAccessible(true);// 把方法设为public，让外部可以调用
            Log.i(TAG,"string--->"+method.getName());
            String string=(String)method.invoke(null,new Object[]{"20181114"});// 调用方法并获取返回值
            Log.i(TAG,"string--->"+string);
            Toast.makeText(this,string,Toast.LENGTH_LONG).show();
        }catch(Exception exception){
            // Handle exception gracefully here.
            exception.printStackTrace();
            Log.e(TAG,exception.getMessage());
        }




    }

    public void startHandlerThread() {
        Intent intent = new Intent(MainActivity.this, HandlerThreadActivity.class);
        startActivity(intent);
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

    private void testAsyncTask() {

        new TestAsynctask(MainActivity.this).execute("https://8a2bc9359a8b355066a9ceb9eeaaac63.dd.cdntips.com/download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk");

    }

    private void testThread() {
        final ThreadLocal<String> localName = new ThreadLocal() {
            @Override
            protected Object initialValue() {
                return Thread.currentThread().getName();
            }
        };
        localName.set("测试");
        String name = localName.get();
        Log.i("Main", name);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String name = localName.get();
                Log.i("Main->son", name);
            }
        }).start();


        //测试Synchronized
//        TestSynchronized instance = new TestSynchronized();
//        Thread t1 = new Thread(instance);
//        Thread t2 = new Thread(instance);
//        t1.start();
//        t2.start();
//        try {
//            t1.join();
//            t2.join();
//            Toast.makeText(this, TestSynchronized.i + "", Toast.LENGTH_SHORT).show();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        //测试SynchronizedBad
        Thread t1 = new Thread(new TestSynchronizedBad());
        Thread t2 = new Thread(new TestSynchronizedBad());
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
//            Toast.makeText(this, TestSynchronizedBad.i + "", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int availablePro = Runtime.getRuntime().availableProcessors();
        Toast.makeText(this, availablePro + "", Toast.LENGTH_SHORT).show();
    }
}

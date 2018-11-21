package com.seven.thread;

import android.util.Log;

public class TestSynchronizedBad implements Runnable{
    //共享资源(临界资源)
    static int i=0;

    /**
     * 作用于静态方法,锁是当前class对象,也就是
     * AccountingSyncClass类对应的class对象
     */
    public static synchronized void increase(){
        i++;
    }

    /**
     * synchronized 修饰实例方法
     */
    public synchronized void increase1(){
        i++;
    }
    @Override
    public void run() {
        for(int j=0;j<1000000;j++){
            increase1();
        }
    }
}

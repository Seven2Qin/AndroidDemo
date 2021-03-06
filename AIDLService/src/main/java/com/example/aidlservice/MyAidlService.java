package com.example.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

public class MyAidlService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iMyAidl;
    }


    private final IMyAidl.Stub iMyAidl = new IMyAidl.Stub() {
        @Override
        public int handle(int value1, int value2) throws RemoteException {
            return value1 + value2;
        }
    };
}

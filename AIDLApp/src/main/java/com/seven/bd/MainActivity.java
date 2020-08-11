package com.seven.bd;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aidlservice.IMyAidl;

public class MainActivity extends AppCompatActivity {


    private IMyAidl iMyAidl;
    private MyServiceConnection myServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initService();
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (iMyAidl != null) {
                        int result = iMyAidl.handle(1, 1);
                        Toast.makeText(MainActivity.this, "add:" + result, Toast.LENGTH_SHORT).show();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(myServiceConnection);
        myServiceConnection = null;
    }

    private void initService() {
        myServiceConnection = new MyServiceConnection();
        Intent i = new Intent();
        i.setAction("com.example.aidlserivce.IMyAidl");
        i.setPackage("com.example.aidlserivce");
        bindService(i, myServiceConnection, Context.BIND_AUTO_CREATE);

    }

    private class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidl = IMyAidl.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidl = null;
        }
    }
}

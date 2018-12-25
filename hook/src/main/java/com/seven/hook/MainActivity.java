package com.seven.hook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.btn_go);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApplicationContext().startActivity(new Intent(MainActivity.this, HookActivity.class));
//                startActivity(new Intent(MainActivity.this, HookActivity.class));
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        Log.i(TAG,"MainActivity --> attachBaseContext");
        try {
            HookHelper.attachContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

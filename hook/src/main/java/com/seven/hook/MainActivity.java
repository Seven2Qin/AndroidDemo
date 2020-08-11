package com.seven.hook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends BaseActivity {

    private static final String TAG = "Hook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
//            HookHelper.attachActivity(MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_main);
        TextView tv = findViewById(R.id.btn_go);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getApplicationContext().startActivity(new Intent(MainActivity.this, HookActivity.class));
                startActivity(new Intent(MainActivity.this, HookActivity.class));
            }
        });
    }

}

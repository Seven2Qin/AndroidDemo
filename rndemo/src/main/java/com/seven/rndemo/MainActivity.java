package com.seven.rndemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.seven.rn.preloader.PreLoaderReactActivity;
import com.seven.rn.preloader.ReactNativePreLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvOne;
    private TextView tvTwo;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvOne = findViewById(R.id.tv_one);
        tvTwo = findViewById(R.id.tv_two);
        tvOne.setOnClickListener(this);
        tvTwo.setOnClickListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            Log.i("Seven","onWindowFocusChanged");
            ReactNativePreLoader.preLoad(MainActivity.this,"2.bundle");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_one:
                intent = new Intent(MainActivity.this, PreLoaderReactActivity.class);
                break;
            case R.id.tv_two:
                intent = new Intent(MainActivity.this, EnterActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Seven","onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Seven","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Seven","onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Seven","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Seven","onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Seven","onRestart");
    }
}

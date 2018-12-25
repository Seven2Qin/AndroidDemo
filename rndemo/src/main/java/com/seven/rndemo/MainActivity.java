package com.seven.rndemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.seven.rn.BaseReactActivity;

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

        intent = new Intent(MainActivity.this, RNActivity.class);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_one:
                intent.putExtra(BaseReactActivity.BUNDLE_NAME, "1.bundle");
                break;
            case R.id.tv_two:
                intent.putExtra(BaseReactActivity.BUNDLE_NAME, "2.bundle");
                break;
        }
        startActivity(intent);

    }
}

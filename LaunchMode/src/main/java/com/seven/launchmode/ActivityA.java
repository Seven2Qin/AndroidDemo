package com.seven.launchmode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityA extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        findViewById(R.id.btn_go).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        startActivity(new Intent(this,ActivityD.class));

    }
}
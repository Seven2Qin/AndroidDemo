package com.seven.javademo;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view = new View(this);
        Log.i(TAG,"hashCode:"+view.hashCode());
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    Thread.sleep(10000);
                    Toast.makeText(MainActivity.this,"hello",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,WebviewActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Integer a = 10;
        Integer b = 10;
        Integer c = 150;
        Integer d = 150;
        Log.i("seven","a == b:"+(a == b));
        Log.i("seven","c == d:"+(c == d));

        //初始化栈(无元素)
        Stack stack = new Stack(new Node(), new Node());

        //栈顶和栈尾是同一指向
        stack.stackBottom = stack.stackTop;

        //指向null
        stack.stackTop.next = null;


        //进栈
        Stack.pushStack(stack, 3);
//        Stack.pushStack(stack, 4);
//        Stack.pushStack(stack, 5);

        Stack.traverse(stack);



        Log.i("seven","SonClass:"+SonClass.getParentName());

        String classFile = "com.jd.". replaceAll(".", "/") + "MyClass.class";
        System.out.println(classFile);

    }

}

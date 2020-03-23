package com.luying.aop_javapoet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.luying.annotation.LRouter;

@LRouter(path = "")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

package com.luying.aop_javapoet;

import android.os.Bundle;

import com.luying.annotation.LRouter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Create by luying
 * 2020-03-23
 */
@LRouter(path = "")
public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

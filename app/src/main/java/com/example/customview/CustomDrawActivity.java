package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CustomDrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SimgleDraw(this));
    }

}
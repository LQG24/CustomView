package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout mRootCLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootCLayout =findViewById(R.id.root_clayout);
        mRootCLayout.addView(new SimgleDraw(this));
    }
}

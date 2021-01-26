package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.customview.Animator.PointView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mRootLLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootLLayout =findViewById(R.id.root_clayout);
//        mRootLLayout.addView(new SimgleDraw(this));
//        mRootLLayout.addView(new MyRegionView(this));
//        mRootLLayout.addView(new DrawText(this),new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mRootLLayout.addView(new PointView(this),new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}

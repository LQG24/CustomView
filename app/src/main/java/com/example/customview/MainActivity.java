package com.example.customview;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customview.Animator.OAnimPointView;
import com.example.customview.Animator.PointView;
import com.example.customview.View.CustomLayoutManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mRootLLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootLLayout = findViewById(R.id.root_clayout);

        addSingleDraw();
//        mRootLLayout.addView(new MyRegionView(this));
//        mRootLLayout.addView(new DrawText(this),new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    private void addSingleDraw() {
        mRootLLayout.addView(new SimgleDraw(this));
    }

    public void onToAnimator(View view) {
        startActivity(new Intent(this, AnimatorActivity.class));
    }

    public void onToTestLayout(View view) {
        startActivity(new Intent(this, TestLayoutActivity.class));
    }

    public void onFlowLayout(View view) {
        startActivity(new Intent(this, FlowActivity.class));
    }

    public void onCustomLayoutManage(View view) {
        startActivity(new Intent(this, CustomLayoutManagerActivity.class));
    }


}

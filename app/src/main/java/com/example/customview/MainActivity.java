package com.example.customview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.event_conflict.EventConflictActivity;
import com.example.customview.event_conflict.EventConflictInnerActivity;

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

    public void onEventConflict(View view) {
        startActivity(new Intent(this, EventConflictActivity.class));
    }

    public void onEventConflictInner(View view){
        startActivity(new Intent(this, EventConflictInnerActivity.class));
    }
}

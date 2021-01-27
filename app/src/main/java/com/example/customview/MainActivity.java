package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.customview.Animator.OAnimPointView;
import com.example.customview.Animator.PointView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mRootLLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mRootLLayout =findViewById(R.id.root_clayout);


        setContentView(R.layout.activity_test_layout);


//        addSingleDraw();
//        mRootLLayout.addView(new MyRegionView(this));
//        mRootLLayout.addView(new DrawText(this),new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        mRootLLayout.addView(new PointView(this),new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        addObjectAnimatorView();
    }

    /**
     *设置pointRadius属性
     *
     * ObjectAnimator  属性第一个字母大写 并加上set 调用自身的方法
     * 例如:在ObjectAnimator中，则是先根据属性值拼装成对应的set函数的名字，
     *      比如这里的scaleY的拼装方法就是将属性的第一个字母强制大写后，与set拼接，所以就是setScaleY。
     *      然后通过反射找到对应控件的setScaleY(float scaleY)函数，将当前数字值做为setScaleY(float scale)的参数将其传入。
     *
     * ObjectAnimator 会动态调用OAnimPointView 自身的setPointRadius
     *
    * */
    private void addObjectAnimatorView() {
        final OAnimPointView oAnimPointView = new OAnimPointView(this);
        mRootLLayout.addView(oAnimPointView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(oAnimPointView,"pointRadius",0,300,0);
                objectAnimator.setDuration(3000);
                objectAnimator.setRepeatCount(-1);
                objectAnimator.start();
            }
        },2000);

    }

    private void addSingleDraw() {
        mRootLLayout.addView(new SimgleDraw(this));
    }
}

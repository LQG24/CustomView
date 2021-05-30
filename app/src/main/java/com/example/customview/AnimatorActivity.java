package com.example.customview;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.Animator.OAnimPointView;
import com.example.customview.Animator.PointView;
import com.example.customview.View.ScrollerLayout;

public class AnimatorActivity extends AppCompatActivity {
    private LinearLayout rootLayout;
    private ScrollerLayout mScrollerLayout;
    private boolean toLeft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator_layout);
        rootLayout = findViewById(R.id.root_layout);
        mScrollerLayout = findViewById(R.id.scroller_layout);

        //        addObjectAnimatorView();
    }

    public void onScroller(View view) {
        if (!toLeft) {
            //只针对内容滑动
            mScrollerLayout.smoothScrollTo(-500, 0);
            toLeft = true;
        } else {
            mScrollerLayout.smoothScrollTo(0, 0);
            toLeft = false;
        }

    }

    public void onDrawPointWithAnimator(View view) {
        rootLayout.addView(new PointView(this), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void onChangePointRadius(View view){
        addObjectAnimatorView();
    }

    /**
     * 设置pointRadius属性
     * <p>
     * ObjectAnimator  属性第一个字母大写 并加上set 调用自身的方法
     * 例如:在ObjectAnimator中，则是先根据属性值拼装成对应的set函数的名字，
     * 比如这里的scaleY的拼装方法就是将属性的第一个字母强制大写后，与set拼接，所以就是setScaleY。
     * 然后通过反射找到对应控件的setScaleY(float scaleY)函数，将当前数字值做为setScaleY(float scale)的参数将其传入。
     * <p>
     * ObjectAnimator 会动态调用OAnimPointView 自身的setPointRadius
     */
    private void addObjectAnimatorView() {
        final OAnimPointView oAnimPointView = new OAnimPointView(this);
        rootLayout.addView(oAnimPointView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(oAnimPointView, "pointRadius", 0, 300, 0);
                objectAnimator.setDuration(3000);
                objectAnimator.setRepeatCount(-1);
                objectAnimator.start();
            }
        }, 2000);

    }
}

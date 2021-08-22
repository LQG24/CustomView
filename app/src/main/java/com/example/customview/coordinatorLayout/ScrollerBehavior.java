package com.example.customview.coordinatorLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ScrollerBehavior extends CoordinatorLayout.Behavior<RecyclerView> {
    public ScrollerBehavior() {
    }

    public ScrollerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        return dependency instanceof TextView;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
//        会让View 在父容器里面上下移动，原理是改变了View 的mTop 的值，
//        但是一旦调用requestLayout，OffsetTopAndBottom 发生的改变就会被清除
//        ，View 又会回到最开始的位置，因为mTop 被重新赋值了
        ViewCompat.offsetTopAndBottom(child,dependency.getBottom() - child.getTop());
        return false;
    }
}

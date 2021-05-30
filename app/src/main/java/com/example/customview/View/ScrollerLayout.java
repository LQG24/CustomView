package com.example.customview.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class ScrollerLayout extends LinearLayout {
    private Scroller mScroller;

    public ScrollerLayout(Context context) {
        super(context);
        init(context);
    }

    public ScrollerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    //缓慢滑动到指定位置
    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int deltaX = destX - scrollX;

        mScroller.startScroll(scrollX, 0, deltaX, 0, 1000);
        //这将会触发onDraw()
        invalidate();
    }

    //每次onDraw()，都会调用computeScroll
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //这将会触发onDraw()
            postInvalidate();
        }
    }
}

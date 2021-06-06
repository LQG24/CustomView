package com.example.customview.event_conflict;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
//事件冲突内部拦截法
public class RecyclerViewEx extends RecyclerView {
    private static final String TAG = "RecyclerViewEx";
    private ViewGroup parent;
    //分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public RecyclerViewEx(@NonNull Context context) {
        super(context);
    }

    public RecyclerViewEx(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewEx(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setParent(ViewGroup parent) {
        this.parent = parent;
    }

   /****父容器不拦截任何事件，所有的事件都传递给子元素，
    * 如果子元素需要此事件就直接消耗掉，否则就交由父容器进行处理。
    * 这种方法和事件分发机制不一致，
    * 需要配合requestDisallowInterceptTouchEvent()才能正常工作。
    * 需要重写子元素的dispatchTouchEvent()，还需要重写父元素的onInterceptTouchEvent()。
    * */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                parent.requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:
                float deltaX = x - mLastX;
                float deltaY = y - mLastY;

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    parent.requestDisallowInterceptTouchEvent(false);
                }
                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        mLastX = x;
        mLastY = y;

        return super.dispatchTouchEvent(event);

    }
}

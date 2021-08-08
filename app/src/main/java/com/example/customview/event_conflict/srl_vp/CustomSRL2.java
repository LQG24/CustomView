package com.example.customview.event_conflict.srl_vp;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.lang.reflect.Field;

public class CustomSRL2 extends SwipeRefreshLayout {

    private int lastX;
    private int lastY;

    public CustomSRL2(Context context) {
        super(context);
    }

    public CustomSRL2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    // 能不能直接调用到ViewGroup
    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        Class clazz = ViewGroup.class;

        try {
            Field mGroupFlagsField =  clazz.getDeclaredField("mGroupFlags");
            mGroupFlagsField.setAccessible(true);
            int c = (int) mGroupFlagsField.get(this);
            Log.e("leo", "dispatchTouchEvent: c " + c);
            if (b) {
                mGroupFlagsField.set(this, 2900051);
            } else {
                mGroupFlagsField.set(this, 2245715);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        super.requestDisallowInterceptTouchEvent(b);
    }

    /**
     * 内部拦截法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.e("leo", "onInterceptTouchEvent: "+ev.getAction());

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            super.onInterceptTouchEvent(ev);
            return false;
        }
        return true;
    }


//    /**
//     * 外部拦截法解决事件冲突
//     */
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        boolean intercepted = false;
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_UP:
//                intercepted = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if(Math.abs(y-lastY)> Math.abs(x-lastX)){
//                    intercepted = true;
//                }else {
//                    intercepted = false;
//                }
//                Log.i(CustomSRL2.class.getSimpleName(),"onInterceptTouchEvent:ACTION_MOVE:intercepted:"+intercepted);
//                break;
//            default:
//                break;
//        }
//        lastX = x;
//        lastY = y;
//        if(!intercepted){
//            super.onInterceptTouchEvent(ev);
//        }
//
//        return intercepted;
//    }

}

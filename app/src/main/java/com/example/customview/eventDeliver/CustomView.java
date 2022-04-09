package com.example.customview.eventDeliver;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {
    private static final String TAG = "CustomView-TAG";
    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG,"dispatchTouchEvent"+":"+"ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG,"dispatchTouchEvent"+":"+"ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG,"dispatchTouchEvent"+":"+"ACTION_UP");
                break;
        }
        Log.i(TAG,"dispatchTouchEvent"+":"+"return:"+ super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG,"onTouchEvent"+":"+"ACTION_DOWN");
                return true;
//                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG,"onTouchEvent"+":"+"ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG,"onTouchEvent"+":"+"ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG,"onTouchEvent"+":"+"ACTION_CANCEL");
                break;
        }
        return super.onTouchEvent(event);
    }
}

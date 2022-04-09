package com.example.customview.eventDeliver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.customview.R;

public class EventDeliverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_deliver);
        //OnTouchListener 优先于 onTouchEvent()对事件进行消费。
        //消费事件返回true，所以事件被消费，不会继续往下传递，View.dispatchTouchEvent()直接返回true；
        //最终不会调用View.onTouchEvent()，也不会调用onClick()
        findViewById(R.id.custom_view).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }
}
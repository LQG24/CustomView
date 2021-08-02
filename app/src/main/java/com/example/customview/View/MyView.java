package com.example.customview.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customview.R;

public class MyView extends View {
    private int width;
    private int height;
    private int bgColor;
    private int shape;
    private Paint mPaint;
    private static final int SHAPE_RECTANGLE = 1;
    private static final int SHAPE_OVAL = 2;



    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        width = array.getInteger(R.styleable.MyView_custom_width, 50);
        height = array.getInteger(R.styleable.MyView_custom_height, 50);
        bgColor = array.getColor(R.styleable.MyView_bgColor, Color.parseColor("#ff0000"));
        shape = array.getInteger(R.styleable.MyView_shape, 1);
        array.recycle();

        mPaint = new Paint();
        mPaint.setColor(bgColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(shape == SHAPE_RECTANGLE){
            Rect rect = new Rect(0,0,width,height);
            canvas.drawRect(rect,mPaint);
        }else {
            Path ovalPath = new Path();
            RectF rect = new RectF(0,0,width,height);
            canvas.drawPath(ovalPath,mPaint);
            canvas.drawOval(rect,mPaint);
        }

    }
}

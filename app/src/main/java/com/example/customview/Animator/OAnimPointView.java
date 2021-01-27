package com.example.customview.Animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;


/**
 * 自定义画圆再加动效 objectAnimator
 *
 * https://blog.csdn.net/harvic880925/article/details/50598322
 * */
public class OAnimPointView extends View {

    private Point mCurPoint;

    public OAnimPointView(Context context) {
        super(context);
        mCurPoint = new Point(10);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(300, 300, mCurPoint.getRadius(), paint);
    }



    /**
     * ObjectAnimator 播放时
     * 会被动态调用
    * */
    void setPointRadius(int radius) {
        mCurPoint.setRadius(radius);
        invalidate();
    }


    public class Point {
        private int radius;

        Point(int radius) {
            this.radius = radius;
        }

        int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }
    }

}

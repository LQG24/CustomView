package com.example.customview.Animator;

import android.animation.ArgbEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;

/**
 * 自定义画圆再加动效
 *
 * https://blog.csdn.net/harvic880925/article/details/50546884
* */
public class PointView extends View {
    private Point mCurPoint;
    private int mColor = 0;
    private Handler mHandler = new Handler();
    public PointView(Context context) {
        super(context);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doPointAnim();
                changeColor();
            }
        },2000);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mCurPoint != null){
            Paint paint = new Paint();
            if(mColor != 0){
                paint.setColor(mColor);
            }

            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(300,300,mCurPoint.getRadius(),paint);
        }
    }

    public void doPointAnim(){
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(),new Point(20),new Point(200));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setRepeatCount(-1);
        animator.setDuration(3000);
//        animator.setInterpolator(new BounceInterpolator());
//        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void changeColor(){
        ValueAnimator animator = ValueAnimator.ofInt(0xffff0000,0xff0000ff);
        //设置ARGB评估器
        animator.setEvaluator(new ArgbEvaluator());


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mColor = (int) animation.getAnimatedValue();
//                invalidate();
            }
        });

        animator.setDuration(3000);
        animator.setRepeatCount(-1);
        animator.start();
    }

    private class Point {
        private int radius;
        Point(int radius){
            this.radius = radius;
        }
        int getRadius(){
            return radius;
        }
        public void setRadius(int radius){
            this.radius = radius;
        }
    }

    /**
     * 自定义Evaluator
     *
     * 计算出当前进度下把对应的值
    * */
    private class PointEvaluator implements TypeEvaluator<Point> {
        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            int start = startValue.getRadius();
            int end = endValue.getRadius();
            int curValue = (int)(start+(end -start)*fraction);
            return new Point(curValue);
        }
    }
}

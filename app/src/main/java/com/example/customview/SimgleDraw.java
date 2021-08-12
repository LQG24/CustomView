package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

/**
 * https://blog.csdn.net/harvic880925/article/details/38875149
 */
public class SimgleDraw extends View {

    private Context mContext;

    public SimgleDraw(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        //设置填充样式
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        //设置阴影，现在还看不出效果
        paint.setShadowLayer(10, -15, -15, Color.GREEN);

        canvas.drawRGB(255, 255, 0);

        //画圆角矩形
        RectF rectF = new RectF(100, 100, 300, 200);
        canvas.drawRoundRect(rectF, 20, 10, paint);

        //椭圆型  椭圆是根据矩形生成的，以矩形的长为椭圆的X轴，矩形的宽为椭圆的Y轴，建立的椭圆图形
        RectF rectf1 = new RectF(300, 210, 400, 300);
        paint.setShadowLayer(0, -15, -15, Color.GREEN);
        paint.setColor(Color.BLUE);
        canvas.drawRect(rectf1, paint);
        paint.setColor(Color.GRAY);
        canvas.drawOval(rectf1, paint);

        //弧 弧是椭圆的一部分，而椭圆是根据矩形来生成的，所以弧当然也是根据矩形来生成的；
        /**
         * 参数：
         * RectF oval:生成椭圆的矩形
         * float startAngle：弧开始的角度，以X轴正方向为0度
         * float sweepAngle：弧持续的角度
         * boolean useCenter:是否有弧的两边，True，还两边，False，只有一条弧
         * */
        RectF rectf2 = new RectF(400, 310, 500, 400);
        canvas.drawArc(rectf2, 0, 90, true, paint);

//        RectF rectf3 = new RectF(450,360,350,450);
        paint.setColor(Color.RED);
        canvas.drawArc(rectf2, 0, 90, false, paint);


        RectF rectF1 = new RectF(200, 400, 800, 600);
        paint.setColor(Color.LTGRAY);
        canvas.drawRect(rectF1, paint);


        paint.setColor(Color.RED);
        canvas.drawLine(200, 500, 800, 500, paint);

        paint.setColor(Color.GREEN);
        canvas.drawLine(500, 400, 500, 600, paint);


        /**
         * 文字高度居中View
         * Height/2  + (decent - accent)/2 - decent
         * = Height/2 + decent/2  -accent/2 -decent
         * = Height/2 - (decent + accent)/2
         * 我的理解：假设基线是从底部decent位置画的，所以居中会是Height/2  + (decent - accent)/2，实际上是在baseline上画的，所以应该再减去decent高度
         *
         *
         * 文字宽度居中View：
         * float width = paint.measureText(mText);
         * paint.setTextAligin(Paint.Align.LEFT);
         * x = getWidth()/2-width/2
         */
        String text = "文字居中控件";
        paint.setColor(Color.BLACK);
        paint.setTextSize(50);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float halfHeight = (rectF1.bottom + rectF1.top) / 2;
        float halfWidth = (rectF1.right + rectF1.left) / 2;
        float textWidth = paint.measureText(text);
        Log.i("lqg", "halfHeight:" + halfHeight + ",halfWidth:" + halfWidth + ",textWidth:" + textWidth);
//        float baseLine = halfHeight + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseLine = halfHeight + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
        float originX = halfWidth - textWidth / 2;

        canvas.drawText(text, originX, baseLine, paint);


    }

}

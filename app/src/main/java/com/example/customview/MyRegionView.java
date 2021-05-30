package com.example.customview;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 自定义控件之绘图篇（三）：区域（Range）
 * https://blog.csdn.net/harvic880925/article/details/39056701
 */
public class MyRegionView extends View {
    private Bitmap mBitmap;

    public MyRegionView(Context context) {
        super(context);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round, null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        //构造一个椭圆路径
        Path ovalPath = new Path();
        RectF rectF = new RectF(50, 50, 200, 500);
        ovalPath.addOval(rectF, Path.Direction.CCW);
        canvas.drawPath(ovalPath, paint);

        paint.setColor(Color.GREEN);
        //SetPath 时，传入一个比椭圆区域小的矩形区域，让其取交集
        Region region = new Region();
        region.setPath(ovalPath, new Region(50, 50, 200, 200));

        //画出路径
//        drawRegion(canvas, region, paint);

//        canvas.drawBitmap(mBitmap, 0, 0, null);

//        Rect src = new Rect(0, 0, 100, 100);
//        canvas.drawRect(src, paint);

        int left = -mBitmap.getWidth() / 2;
        int top = -mBitmap.getHeight()/2;


        Rect dst = new Rect(left, top, mBitmap.getWidth() + left, top+mBitmap.getHeight());
        canvas.drawRect(dst, paint);
        canvas.drawBitmap(mBitmap, null, dst, null);


    }

    /**
     * 首先，根据区域构建一个矩形集，然后利用next(Rect r)来逐个获取所有矩形，
     * 绘制出来，最终得到的就是整个区域，
     * 如果我们将上面的画笔Style从FILL改为STROKE，重新绘制椭圆路径，会看得更清楚
     */
    private void drawRegion(Canvas canvas, Region region, Paint paint) {
        RegionIterator iterator = new RegionIterator(region);
        Rect r = new Rect();
        while (iterator.next(r)) {
            canvas.drawRect(r, paint);
        }
    }
}

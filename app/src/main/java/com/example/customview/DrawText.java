package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
/**
 * drawText的四线格 可以参考draw_text.png
 *
 * https://blog.csdn.net/harvic880925/article/details/50423762
* */
public class DrawText extends View {
    public DrawText(Context context) {
        super(context);
    }

    /**
     * ascent线Y坐标 = baseline线的y坐标 + fontMetric.ascent；
     * descent线Y坐标 = baseline线的y坐标 + fontMetric.descent；
     * top线Y坐标 = baseline线的y坐标 + fontMetric.top；
     * bottom线Y坐标 = baseline线的y坐标 + fontMetric.bottom；
    * */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int baseLineY = 200;
        int baseLineX = 0;

        Paint paint = new Paint();
        //写文字
        paint.setColor(Color.GREEN);
        paint.setTextSize(120);//以px为单位
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("harvic\'s blog",baseLineX,baseLineY,paint);

        //计算各线位置
        Paint.FontMetrics fm  = paint.getFontMetrics();
        float ascent = fm.ascent+baseLineY;
        float descent = fm.descent+baseLineY;

        float top = fm.top+baseLineY;
        float bottom = fm.bottom+baseLineY;

        //画基线 红色
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX,baseLineY,3000,baseLineY,paint);

        //画top线 蓝色
        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX,top,3000,top,paint);

        //画ascent 绿色
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX,ascent,3000,ascent,paint);

        //descent 黄色
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX,descent,3000,descent,paint);

        //bottom 灰色
        paint.setColor(Color.GRAY);
        canvas.drawLine(baseLineX,bottom,3000,bottom,paint);
    }
}

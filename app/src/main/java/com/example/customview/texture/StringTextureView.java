package com.example.customview.texture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import java.util.Random;

/**
 * Author:linqiaogeng
 * Date:2022/9/29
 * Desc:随机字符串
 * @see :https://blog.csdn.net/yu540135101/article/details/103066474
 */
public class StringTextureView extends TextureView implements TextureView.SurfaceTextureListener {
    public static final String TAG = StringTextureView.class.getSimpleName();
    private Thread thread;//用于绘制的线程
    private boolean isRunning;//线程的控制开关
    private Canvas canvas;
    private Paint paint;
    private int fps = 1;

    public StringTextureView(Context context) {
        this(context, null);
    }

    public StringTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StringTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setSurfaceTextureListener(this);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setTextSize(50);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        isRunning = true;
        drawSomething();
    }

    private void drawSomething() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //不断绘制
                while (isRunning) {
                    long startMs = System.currentTimeMillis();
                    draw();
                    long endMs = System.currentTimeMillis();
                    long needTime = 1000 / fps;
                    long usedTime = endMs - startMs;

                    if (usedTime < needTime) {
                        try {
                            Thread.sleep(needTime - usedTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();
    }

    private void draw() {
        //View被销毁，但是子线程可能还在进行操作，可能抛出一些异常
        try {
            canvas = lockCanvas();
            //按home或者back，SurfaceView被销毁，所以需要判空canvas
            if(canvas != null){
                //黑色清屏
                canvas.drawColor(Color.BLACK);
                drawTextWithCenterPoint(canvas, getWidth() / 2, getHeight() / 2, getRandomString(10), paint);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (canvas != null) {
                unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        isRunning = false;
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    /**
     * 获取一条随机字符串
     * @param length
     * @return
     */
    public String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        Log.e(TAG, "getRandomString: " + sb.toString());
        return sb.toString();
    }

    /**
     * 以中心点绘制文字
     *
     * @param canvas
     * @param centerX
     * @param centerY
     * @param text
     * @param paint
     */
    private void drawTextWithCenterPoint(Canvas canvas, int centerX, int centerY, String text, Paint paint) {
        //获取文本的宽度，但是是一个比较粗略的结果
        float textWidth = paint.measureText(text);
        //文字度量
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //得到基线的位置
        float baselineY = centerY + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        //绘制
        canvas.drawText(text, centerX - textWidth / 2, baselineY, paint);
    }
}

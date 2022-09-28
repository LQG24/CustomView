package com.example.customview.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Author:linqiaogeng
 * Date:2022/9/28
 * Desc: Android中的SurfaceView详解
 * @see  https://www.jianshu.com/p/b037249e6d31
 */
public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder mSurfaceHolder;
    //绘图的Canvas
    private Canvas mCanvas;
    //子线程标志位
    private boolean mIsDrawing;

    public SurfaceViewTemplate(Context context) {
        this(context,null);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SurfaceViewTemplate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        //开启子线程
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public void run() {
        while (mIsDrawing){
            drawSomething();
        }
    }

    private void drawSomething() {
        try {
            //获得canvas对象
            mCanvas = mSurfaceHolder.lockCanvas();
            //绘制背景
            mCanvas.drawColor(Color.WHITE);
            //绘图
        }catch (Exception e){

        }finally {
            if (mCanvas != null){
                //释放canvas对象并提交画布
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    private void initView() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
    }
}

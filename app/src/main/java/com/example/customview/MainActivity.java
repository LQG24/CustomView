package com.example.customview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.NestedScrolling.NestedTraditionActivity;
import com.example.customview.NestedScrolling.nested_scroll_parent_child.NestScrollActivity;
import com.example.customview.coordinatorLayout.CustomBehaviorActivity;
import com.example.customview.eventDeliver.EventDeliverActivity;
import com.example.customview.event_conflict.EventConflictActivity;
import com.example.customview.event_conflict.EventConflictInnerActivity;
import com.example.customview.event_conflict.srl_vp.SRL_VP_main;
import com.example.customview.utils.SystemUtils;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mRootLLayout;
    private ImageView levelImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRootLLayout = findViewById(R.id.root_clayout);
        levelImage = findViewById(R.id.custom_img);

        addSingleDraw();
        levelImage.setImageBitmap(drawLevelImage());
//        mRootLLayout.addView(new MyRegionView(this));
//        mRootLLayout.addView(new DrawText(this),new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private Bitmap drawLevelImage() {
        Bitmap levelBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.nn_level_bg_color_1);

        // 徽章宽高
        int badgeWidth = SystemUtils.dip2px(this, 9);
        int badgeHeight = SystemUtils.dip2px(this, 14);


        // 左右边距
        int textPaddingLeft = SystemUtils.dip2px(this, 0.5f);
        int textPaddingRight = SystemUtils.dip2px(this, 4f);
        int iconPaddingLeft = SystemUtils.dip2px(this, 5f);

        int level = 10;
        // 计算出字的宽度
        Paint mPaint = new Paint();
        mPaint.setTextSize(SystemUtils.dip2px(this, 10));
        mPaint.setColor(Color.parseColor("#ffB0CFD3"));
        mPaint.setAntiAlias(true);
        Rect rect = new Rect();
        mPaint.getTextBounds(String.valueOf(level), 0, String.valueOf(level).length(), rect);

        // 最小宽度
        int miniWidth = SystemUtils.dip2px(this, 10f);

        int textWidth = Math.max(rect.width(), miniWidth);
        // 总宽度 = 图标宽度 + 加上文字的宽度
        int width = badgeWidth + iconPaddingLeft + textWidth + textPaddingLeft + textPaddingRight;


        // 计算宽高
        Bitmap newBitmap = Bitmap.createBitmap(width, badgeHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);


        // 画圆角矩形
        Paint rectPaint = new Paint();
        // 圆角
        int radius = SystemUtils.dip2px(this, 2);
        // 线宽度
        int strokeWidth = SystemUtils.dip2px(this, 1);

        rectPaint.setStrokeWidth(strokeWidth);
        rectPaint.setFakeBoldText(true);
        rectPaint.setColor(Color.GRAY);
        rectPaint.setAntiAlias(true);
        // 设置空心
        rectPaint.setStyle(Paint.Style.STROKE);
        // 设置个新的长方形
        RectF oval = new RectF(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2, badgeHeight - strokeWidth / 2);
        // 第二个参数是x半径，第三个参数是y半径
        canvas.drawRoundRect(oval, radius, radius, rectPaint);

        // 画等级
        float scale = badgeWidth * 1.0f / levelBitmap.getWidth();
        canvas.drawBitmap(scaleBitmap(levelBitmap, scale), iconPaddingLeft, 0, mPaint);

        // 画文字
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = badgeHeight / 2 + dy;

        int textX = 0;
        if (textWidth > miniWidth) {
            // 计算位置(位于icon后的位置)
            textX = badgeWidth + iconPaddingLeft + textPaddingLeft;
        } else {
            // 计算位置(除去icon的位置后 居中显示)
            textX = (width - iconPaddingLeft - textPaddingLeft - textPaddingRight - badgeWidth - textWidth) / 2 + badgeWidth + textPaddingLeft + iconPaddingLeft;
        }
        canvas.drawText(String.valueOf(level), textX, baseLine, mPaint);

        return newBitmap;
    }


    /**
     * 对bitmap进行缩放
     *
     * @param bitmap
     * @param scale
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, float scale) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        // 计算宽高
        int originWidth = bitmap.getWidth();
        int originHeight = bitmap.getHeight();

        if (scale == 1.0f) {
            // 宽高不变，则不去缩放
            return bitmap;
        } else {
            // 否则按高度进行缩放
            // Bitmap进行缩放
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            return Bitmap.createBitmap(bitmap, 0, 0, originWidth, originHeight, matrix, true);
        }
    }

    private void addSingleDraw() {
        mRootLLayout.addView(new SimgleDraw(this));
    }

    public void onToAnimator(View view) {
        startActivity(new Intent(this, AnimatorActivity.class));
    }

    public void onToTestLayout(View view) {
        startActivity(new Intent(this, TestLayoutActivity.class));
    }

    public void onFlowLayout(View view) {
        startActivity(new Intent(this, FlowActivity.class));
    }

    public void onCustomLayoutManage(View view) {
        startActivity(new Intent(this, CustomLayoutManagerActivity.class));
    }

    public void onEventDeliver(View view){
        startActivity(new Intent(this, EventDeliverActivity.class));
    }

    public void onEventConflict(View view) {
        startActivity(new Intent(this, EventConflictActivity.class));
    }

    public void onEventConflictInner(View view) {
        startActivity(new Intent(this, EventConflictInnerActivity.class));
    }

    public void onEventConflict1(View view){
        startActivity(new Intent(this, SRL_VP_main.class));
    }

    public void onNestedActivity(View view){
        startActivity(new Intent(this, NestScrollActivity.class));
    }

    public void onNestedTraditionActivity(View view){
        startActivity(new Intent(this, NestedTraditionActivity.class));
    }

    public void onDemo02Activity (View view){
        startActivity(new Intent(this, CustomBehaviorActivity.class));
    }

    public void onConstraintLayout(View view){

    }


}

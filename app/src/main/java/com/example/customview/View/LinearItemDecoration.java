package com.example.customview.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.view.View;

import com.example.customview.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
/**
 * 自定义ItemDecoration与蒙版效果
* */

public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Bitmap mBitmap;

    public LinearItemDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        BitmapFactory.Options options = new BitmapFactory.Options();
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.badge);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = 200;
        outRect.bottom = 10;
    }

    /**
     *
     * ItemDecoration与Item的绘制顺序为：decoration 的 onDraw->item的 onDraw->decoration 的 onDrawOver，这三者是依次发生的。
     * 所以，onDrawOver 是绘制在最上层的，所以它的绘制位置并不受限制（当然，decoration 的 onDraw 绘制范围也不受限制，只不过不可见，被item所覆盖），
     * 所以利用 onDrawOver 可以做很多事情，
     * 例如为 RecyclerView 整体顶部绘制一个蒙层、超出itemDecoration的范围绘制图像。
    * */

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //画勋章
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            int left = manager.getLeftDecorationWidth(child);
            if (index % 3 == 0) {
                c.drawBitmap(mBitmap, left - mBitmap.getWidth() / 2, child.getTop(), mPaint);
            }
        }

        //画蒙层
        View temp = parent.getChildAt(0);
        LinearGradient linearGradient = new LinearGradient(parent.getWidth() / 2, 0, parent.getWidth() / 2, temp.getHeight() * 3, 0xff0000ff, 0x000000ff, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
        c.drawRect(0, 0, parent.getWidth(), temp.getHeight() * 3, mPaint);
    }
}

package com.example.customview.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * FlowLayout自适应容器实现
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * （1）何时换行
     * 从效果图中可以看到，FlowLayout的布局是一行行的，如果当前行已经放不下下一个控件，那就把这个控件移到下一行显示。
     * 所以我们要有个变量来计算当前行已经占据的宽度，以判断剩下的空间是否还能容得下下一个控件。
     * （2）、如何得到FlowLayout的宽度
     * FlowLayout的宽度是所有行宽度的最大值，所以我们要记录下每一行的所占据的宽度值，进而找到所有值中的最大值。
     * （3）、如何得到FlowLayout的高度
     * 很显然，FlowLayout的高度是每一行高度的总和，而每一行的高度则是取该行中所有控件高度的最大值。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(FlowLayout.class.getSimpleName(), "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        //every line width
        int lineWidth = 0;
        //every line height
        int lineHeight = 0;
        //FlowLayout width
        int width = 0;
        //FlowLayout height
        int height = 0;


        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int marginLeft = layoutParams.leftMargin;
            int marginRight = layoutParams.rightMargin;
            int marginTop = layoutParams.topMargin;
            int marginBottom = layoutParams.bottomMargin;

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            int childWidth = child.getMeasuredWidth() + marginLeft + marginRight;
            int childHeight = child.getMeasuredHeight() + marginTop + marginBottom;


            if (lineWidth + childWidth + paddingRight + paddingLeft > measureWidth) {
                //需要换行
                width = Math.max(childWidth, lineWidth);
                height += lineHeight;
                //因为由于盛不下当前控件，而将此控件调到下一行，所以将此控件的高度和宽度初始化给lineHeight、lineWidth
                lineWidth = childWidth;
                lineHeight = childHeight;
            } else {
                // 否则累加值lineWidth,lineHeight取最大高度
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            //添加最后一行
            if (i == count - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        //增加内间距
        height = height + paddingTop + paddingBottom;
        width = width + paddingLeft + paddingRight;

        setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth : width, (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int count = getChildCount();
        //累加当前行的行宽
        int lineWidth = 0;
        //当前行的行高
        int lineHeight = 0;
        //当前坐标的top坐标和left坐标
        int top = paddingTop, left = paddingLeft;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int marginLeft = layoutParams.leftMargin;
            int marginRight = layoutParams.rightMargin;
            int marginTop = layoutParams.topMargin;
            int marginBottom = layoutParams.bottomMargin;

            int childWidth = child.getMeasuredWidth() + marginLeft + marginRight;
            int childHeight = child.getMeasuredHeight() + marginTop + marginBottom;

            if (childWidth + lineWidth + paddingRight + paddingLeft> getMeasuredWidth()) {
                //如果换行,当前控件将跑到下一行，从最左边开始，所以left就是paddingLeft，而top则需要加上上一行的行高，才是这个控件的top点;
                top += lineHeight;
                left = paddingLeft;

                //同样，重新初始化lineHeight和lineWidth
                lineHeight = childHeight;
                lineWidth = childWidth;

            } else {
                // 否则累加值lineWidth,lineHeight取最大高度
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            int lp = left + marginLeft;
            int tp = top + marginTop;
            int rp = lp + child.getMeasuredWidth();
            int bp = tp + child.getMeasuredHeight();


            child.layout(lp, tp, rp, bp);

            //将left置为下一子控件的起始点
            left += childWidth;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }
}

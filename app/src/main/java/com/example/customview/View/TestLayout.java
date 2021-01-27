package com.example.customview.View;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
/**
 * https://blog.csdn.net/harvic880925/article/details/47029169
* */
public class TestLayout extends ViewGroup {
    public TestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * widthMeasureSpec和heightMeasureSpec转化成二进制数字表示，他们都是32位的。
     * 前两位代表mode(测量模式)，后面30位才是他们的实际数值（size）。
     *
     * （1）模式分类
     * 它有三种模式：
     * ①、UNSPECIFIED(未指定)，父元素部队自元素施加任何束缚，子元素可以得到任意想要的大小；
     * ②、EXACTLY(完全)，父元素决定自元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
     * ③、AT_MOST(至多)，子元素至多达到指定大小的值。
     * 他们对应的二进制值分别是：
     * UNSPECIFIED=00000000000000000000000000000000
     * EXACTLY =01000000000000000000000000000000
     * AT_MOST =10000000000000000000000000000000
     * 由于最前面两位代表模式，所以他们分别对应十进制的0，1，2；
     *
     *
     * wrap_content-> MeasureSpec.AT_MOST
     * match_parent -> MeasureSpec.EXACTLY
     * 具体值 -> MeasureSpec.EXACTLY
     *
     *
     *    对应11000000000000000000000000000000;总共32位，前两位是1
     *int MODE_MASK  = 0xc0000000;
     *
     *    提取模式
     * public static int getMode(int measureSpec) {
     *     return (measureSpec & MODE_MASK);
     *
     *    提取数值
     * public static int getSize(int measureSpec) {
     *     return (measureSpec & ~MODE_MASK);
     * }
    * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int height = 0;
        int width = 0;
        int count = getChildCount();
        for (int i=0;i<count;i++){
            //测量子控件
            View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            //获取子控件的高度和宽度
//            int childHeight = child.getMeasuredHeight();
//            int childWidth = child.getMeasuredWidth();
//            Log.i(TestLayout.class.getSimpleName(),"childWidth:"+childWidth+",childHeight:"+childHeight);

            //获取有margin的子控件宽和高
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            int childHeight = child.getMeasuredHeight()+layoutParams.bottomMargin+layoutParams.topMargin;
            int childWidth = child.getMeasuredWidth()+layoutParams.leftMargin+layoutParams.rightMargin;


            //得到最大宽度，并且累加高度
            height+=childHeight;
            width = Math.max(childWidth,width);
        }

        setMeasuredDimension(measureWidthMode == MeasureSpec.EXACTLY? measureWidth:width,measureHeightMode == MeasureSpec.EXACTLY?measureHeight:height);
    }

    /**
     * getMeasuredWidth()与getWidth()
     * 区别主要体现在下面几点：
     *
     * - 首先getMeasureWidth()方法在measure()过程结束后就可以获取到了，而getWidth()方法要在layout()过程结束后才能获取到。
     * - getMeasureWidth()方法中的值是通过setMeasuredDimension()方法来进行设置的，
     *   而getWidth()方法中的值则是通过layout(left,top,right,bottom)方法设置的。
    * */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top =0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            int childHeight = child.getMeasuredHeight();
            int childWidth = child.getMeasuredWidth();

            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
            top+= layoutParams.topMargin;

            child.layout(layoutParams.leftMargin,top,childWidth,top+childHeight);
            top = top+childHeight+layoutParams.bottomMargin;
        }
    }


    /**
     *
     * 我们重写了两个函数，一个是generateLayoutParams（）函数，
     * 一个是generateDefaultLayoutParams（）函数。直接返回对应的MarginLayoutParams（）的实例
     *
     * 在container在初始化子控件时，会调用LayoutParams generateLayoutParams(LayoutParams p)来为子控件生成对应的布局属性，
     * 但默认只是生成layout_width和layout_height所以对应的布局参数，
     * 即在正常情况下的generateLayoutParams（）函数生成的LayoutParams实例是不能够取到margin值的
     * 所以，如果我们还需要margin相关的参数就只能重写generateLayoutParams（）函数了
     *
    * */
    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}

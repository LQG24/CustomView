package com.example.customview.View;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义LayoutManager 实现列表展示 并且支持上下滑动
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    private int mTotalHeight = 0;

    /**
     * 在LayoutManager中，所有Item的布局都是在onLayoutChildren()函数中处理的，
     * 所以我们在CustomLayoutItem中添加onLayoutChildren()函数
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //没有Item,界面空着
        if(getItemCount() == 0){
            detachAndScrapAttachedViews(recycler);
            return;
        }

        //定义竖直方向的偏移量
        int offsetY = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            //item+decoration的总宽度
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);
            layoutDecorated(view, 0, offsetY, width, offsetY + height);
            offsetY += height;
        }

        //如果所有子View的高度和没有填满RecyclerView的高度,
        //则将高度设置为RecyclerView的高度
        mTotalHeight = Math.max(offsetY, getVerticalSpace());
    }

    /**
     * 得到RecyclerView用于显示item的真实高度
     */
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    /**
     * 在canScrollVertically()中return true；使LayoutManager具有垂直滚动的功能
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private int mSumDy = 0;

    /**
     * 在scrollVerticallyBy中接收每次滚动的距离dy
     * <p>
     * 变量mSumDy 保存所有移动过的dy，如果当前移动的距离<0，那么就不再累加dy，直接让它移动到y=0的位置，因为之前已经移动的距离是mSumdy;
     * 所以计算方法为：travel+mSumdy = 0;
     * => travel = -mSumdy
     * 所以要将它移到y=0的位置，需要移动的距离为-mSumdy
     * <p>
     * <p>
     * 判断到底的方法，其实就是我们需要知道所有item的总高度，用总高度减去最后一屏的高度，就是到底的时的偏移值，如果大于这个偏移值就说明超过底部了。
     */
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int travel = dy;
        //如果滑动到最顶部
        if (mSumDy + travel < 0) {
            travel = -mSumDy;
        } else if (mSumDy + dy > mTotalHeight - getVerticalSpace()) {  //mSumDy + dy 表示当前的移动距离,mTotalHeight - getVerticalSpace()表示当滑动到底时滚动的总距离
            travel = mTotalHeight - getVerticalSpace() - mSumDy;
        }
        mSumDy += travel;

        // 平移容器内的item
        offsetChildrenVertical(-travel);
        return dy;
    }
}

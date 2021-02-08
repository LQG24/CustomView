package com.example.customview.View;

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义LayoutManager 实现列表展示 并且支持上下滑动
 * <p>
 * 回收复用主要有两部分：
 * <p>
 * 第一：在onLayoutChildren初始布局时：
 * <p>
 * 使用 detachAndScrapAttachedViews(recycler)将所有的可见HolderView剥离
 * 一屏中能放几个item就获取几个HolderView，撑满初始化的一屏即可，不要多创建
 * 第二：在scrollVerticallyBy滑动时：
 * <p>
 * 先判断在滚动dy距离后，哪些holderView需要回收，如果需要回收就调用removeAndRecycleView(child, recycler)先将它回收。
 * 然后向系统获取HolderView对象来填充滚动出来的空白区域
 * 下面我们就利用这个原理来实现CustomLayoutManager的回收复用功能。
 * <p>
 * <p>
 * 在滚动时，所有移除的View都是使用removeAndRecycleView(child, recycler),千万不要将它与detachAndScrapAttachedViews(recycler)搞混了。
 * 在滚动时，已经超出边界的HolderView是需要被回收的，而不是被detach。
 * detach的意思是暂时存放，立马使用。很显然，我们这里在越界之后，立马使用的可能性不大，所以必须回收。
 * 如果立马使用，它会从mCachedViews中去取。大家也可以简单的记忆，在onLayoutChildren函数中（布局时），
 * 就使用detachAndScrapAttachedViews(recycler)，在scrollVerticallyBy函数中（滚动时），
 * 就使用removeAndRecycleView(child, recycler)，当然能理解就更好啦。
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    private int mTotalHeight = 0;

    private int mItemWidth, mItemHeight;
    private SparseArray<Rect> mItemRects = new SparseArray<>();

    /**
     * 在LayoutManager中，所有Item的布局都是在onLayoutChildren()函数中处理的，
     * 所以我们在CustomLayoutItem中添加onLayoutChildren()函数
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //没有Item,界面空着
        if (getItemCount() == 0) {
            //做一下容错处理，在Adapter中没有数据的时候，直接将当前所有的Item从屏幕上剥离，将当前屏幕清空：
            detachAndScrapAttachedViews(recycler);
            return;
        }
        //1.使用 detachAndScrapAttachedViews(recycler)将所有的可见HolderView剥离
        detachAndScrapAttachedViews(recycler);

        View childView = recycler.getViewForPosition(0);
        //因为我们只有测量过以后，系统才知道它的测量的宽高，如果不测量，系统也是不知道它的宽高的（mItemWidth、mItemHeight）
        measureChildWithMargins(childView, 0, 0);
        mItemWidth = getDecoratedMeasuredWidth(childView);
        mItemHeight = getDecoratedMeasuredHeight(childView);
        //在这里，每个item的高度都是一致的，所以，只需要用RecyclerView的高度除以每个item的高度，
        // 就得到了能一页显示多少个item了
        int visibleCount = getVerticalSpace() / mItemHeight;

        //TODO 复用recyclerView
        //定义竖直方向的偏移量
        int offsetY = 0;
        //这里使用的是getItemCount()，所以会遍历Adapter中所有Item，记录下在初始化时，从上到下的所有Item的位置
        for (int i = 0; i < getItemCount(); i++) {
            Rect rect = new Rect(0, offsetY, mItemWidth, offsetY + mItemHeight);
            mItemRects.put(i, rect);
            offsetY += mItemHeight;
        }
        //只显示可见的布局，不可见的不再布局
        for (int i = 0; i < visibleCount; i++) {
            Rect rect = mItemRects.get(i);
            if (rect != null) {
                View view = recycler.getViewForPosition(i);
                //需要注意的是，因为我们在之前已经使用detachAndScrapAttachedViews(recycler);
                // 将所有view从RecyclerView中剥离，所以，我们需要重新通过addView(view)添加进来。
                // 在添加进来以后，需要走一个这个View的测量和layout逻辑，先经过测量，再将它layout到指定位置。
                // 如果我们没有测量直接layout，会什么都出不来，因为任何view的layout都是依赖measure出来的位置信息的。
                addView(view);
                //addView后一定要measure，先measure再layout
                measureChildWithMargins(view, 0, 0);
                layoutDecorated(view, rect.left, rect.top, rect.right, rect.bottom);
            }
        }

        //TODO 未复用recyclerView
        //定义竖直方向的偏移量
//        int offsetY = 0;
//        for (int i = 0; i < getItemCount(); i++) {
//            View view = recycler.getViewForPosition(i);
//            addView(view);
//            measureChildWithMargins(view, 0, 0);
//            //item+decoration的总宽度
//            int width = getDecoratedMeasuredWidth(view);
//            int height = getDecoratedMeasuredHeight(view);
//            layoutDecorated(view, 0, offsetY, width, offsetY + height);
//            offsetY += height;
//        }

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
        //TODO 未复用recyclerView
//        int travel = dy;
//        //如果滑动到最顶部
//        if (mSumDy + travel < 0) {
//            travel = -mSumDy;
//        } else if (mSumDy + dy > mTotalHeight - getVerticalSpace()) {  //mSumDy + dy 表示当前的移动距离,mTotalHeight - getVerticalSpace()表示当滑动到底时滚动的总距离
//            travel = mTotalHeight - getVerticalSpace() - mSumDy;
//        }
//        mSumDy += travel;
//
//        // 平移容器内的item
//        offsetChildrenVertical(-travel);
//        return dy;
        //TODO 复用recyclerView
        if (getChildCount() <= 0) {
            return dy;
        }

        int travel = dy;
        //如果滑到最顶部
        if (mSumDy + dy < 0) {
            travel = -mSumDy;
        } else if (mSumDy + dy > mTotalHeight - getVerticalSpace()) { //mSumDy + dy 表示当前的移动距离,mTotalHeight - getVerticalSpace()表示当滑动到底时滚动的总距离
            //如果滑动到最顶部
            travel = mTotalHeight - getVerticalSpace() - mSumDy;
        }

        //回收越界子View
        //在判断要回收哪些越界的Item时，我们需要遍历当前所有在显示的item，让它们模拟移动travel距离后，看是不是还在屏幕范围内。
        // 当travel>0时，说明是从下向上滚动，自然是会将顶部的item移除，所以我们只需要判断，当前的item是不是超过了上边界（y=0）即可
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            //需要回收当前屏幕，上越界的View
            if (travel > 0) { //从下向上滚动
                //表示将这个item上移以后，它的下边界的位置，当下边界的位置小于当前的可显示区域的上边界（此时为0）时，就需要将它移除。
                if (getDecoratedBottom(child) - travel < 0) {
                    removeAndRecycleView(child, recycler);
                    continue;
                }
                //回收当前屏幕，下越界的View
            } else if (travel < 0) {
                //利用getDecoratedTop(child) - travel得到在移动travel距离后，这个item的顶部位置，如果这个顶部位置在屏幕的下方，那么它就是不可见的。
                // getHeight() - getPaddingBottom()得到的是RecyclerView可显示的最低部位置
                if (getDecoratedTop(child) - travel > getHeight() - getPaddingBottom()) {
                    removeAndRecycleView(child, recycler);
                    continue;
                }
            }

        }
        //拿到屏幕移动后的可见区域
        Rect visibleRect = getVisibleArea(travel);
        //布局子view阶段
        if (travel >= 0) { //从下向上滚动
            //找到移动前最后一个可见的view
            View lastView = getChildAt(getChildCount() - 1);
            int minPos = getPosition(lastView) + 1;//从最后一个View+1开始吧

            //顺序addChildView
            for (int i = minPos; i <= getItemCount() - 1; i++) {
                Rect rect = mItemRects.get(i);
                //取矩形交集
                if (Rect.intersects(visibleRect, rect)) {
                    View child = recycler.getViewForPosition(i);
                    addView(child);
                    measureChildWithMargins(child, 0, 0);
                    //到目前为止，所有的item并未真正的移动，所以我们在布局时，仍然需要按上次的移动距离来进行布局，所以这里在布局时使用是
                    // layoutDecorated(child, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy)
                    // ,单纯只是减去了mSumDy,并没有同时减去mSumDy和travel，最后才调用offsetChildrenVertical(-travel)来整体移动布局好的item。
                    // 这时才会把我们刚才新增布局上的item显示出来。
                    layoutDecorated(child, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy);
                } else {
                    break;
                }
            }
        } else {
            //先得到在滚动前显示的第一个item的前一个item:
            View firstView = getChildAt(0);
            int maxPos = getPosition(firstView) - 1;

            for (int i = maxPos; i >= 0; i--) {
                Rect rect = mItemRects.get(i);
                if (rect.intersects(visibleRect, rect)) {
                    View child = recycler.getViewForPosition(i);
                    //如果在显示区域，那么，就将它插在第一的位置
                    addView(child, 0);
                    measureChildWithMargins(child, 0, 0);
                    //在布局Item时，由于还没有移动，所以在布局时并不考虑travel的事
                    layoutDecoratedWithMargins(child, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy);
                } else {
                    break;
                }
            }
        }

        mSumDy += travel;
        //平移容器内的Item
        offsetChildrenVertical(-travel);
        return travel;
    }

    //mSumDy表示上次的移动距离，travel表示这次的移动距离，所以mSumDy + travel表示这次移动后的屏幕位置
    //移动后的矩形
    private Rect getVisibleArea(int travel) {
        Rect result = new Rect(getPaddingLeft(), getPaddingTop() + mSumDy + travel, getWidth() + getPaddingRight(), getVerticalSpace() + mSumDy + travel);
        return result;
    }
}

package com.parting_soul.linkagerecyclerviewdemo.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author parting_soul
 * @date 2019/1/13
 */
public class ScrollTopLayoutManager extends GridLayoutManager {

    public ScrollTopLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ScrollTopLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public ScrollTopLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

            /**
             *
             * @param viewStart item的top
             * @param viewEnd item的bottom
             * @param boxStart RecyclerView的top
             * @param boxEnd RecyclerView的bottom
             * @param snapPreference
             * @return 返回item到RecyclerView Top的偏移
             */
            @Override
            public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                return  boxStart - viewStart;
            }

        };
        linearSmoothScroller.setTargetPosition(position);
        this.startSmoothScroll(linearSmoothScroller);
    }

}

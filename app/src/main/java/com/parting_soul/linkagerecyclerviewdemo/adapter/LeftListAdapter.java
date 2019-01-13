package com.parting_soul.linkagerecyclerviewdemo.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.parting_soul.linkagerecyclerviewdemo.R;
import com.parting_soul.linkagerecyclerviewdemo.bean.LeftSortBean;

import java.util.List;

/**
 * @author parting_soul
 * @date 2019/1/13
 */
public class LeftListAdapter extends BaseQuickAdapter<LeftSortBean, BaseViewHolder> {
    private int selectPosition;

    public LeftListAdapter(int layoutResId, @Nullable List<LeftSortBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LeftSortBean item) {
        helper.setText(R.id.tv_sort, item.getTypeName());
        boolean isSelected = helper.getAdapterPosition() == selectPosition;
        helper.setTextColor(R.id.tv_sort, isSelected ? Color.parseColor("#FF1497E8") : Color.parseColor("#000000"));
        helper.itemView.setBackgroundColor(isSelected ? Color.parseColor("#f2f2f2") : Color.parseColor("#ffffff"));
    }

    public void setSelectPosition(int index) {
        notifyItemChanged(selectPosition);
        notifyItemChanged(index);
        selectPosition = index;
    }
}

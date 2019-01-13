package com.parting_soul.linkagerecyclerviewdemo.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.parting_soul.linkagerecyclerviewdemo.R;
import com.parting_soul.linkagerecyclerviewdemo.bean.RightBean;

import java.util.List;

/**
 * @author parting_soul
 * @date 2019/1/13
 */
public class RightListAdapter extends BaseMultiItemQuickAdapter<RightBean, BaseViewHolder> {

    public RightListAdapter(List<RightBean> data) {
        super(data);
        addItemType(RightBean.TYPE_ITEM, R.layout.adapter_right_sort);
        addItemType(RightBean.TYPE_TITLE, R.layout.adapter_item_title);
    }

    @Override
    protected void convert(BaseViewHolder helper, RightBean item) {
        switch (helper.getItemViewType()) {
            case RightBean.TYPE_ITEM:
                helper.setText(R.id.tv_name, item.getName())
                        .setImageResource(R.id.iv_img, item.getImgRes());
                break;
            case RightBean.TYPE_TITLE:
                helper.setText(R.id.tv_title, item.getName());
                break;
            default:
                break;
        }
    }
}

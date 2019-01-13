package com.parting_soul.linkagerecyclerviewdemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.parting_soul.linkagerecyclerviewdemo.adapter.LeftListAdapter;
import com.parting_soul.linkagerecyclerviewdemo.adapter.RightListAdapter;
import com.parting_soul.linkagerecyclerviewdemo.bean.DataBean;
import com.parting_soul.linkagerecyclerviewdemo.bean.LeftSortBean;
import com.parting_soul.linkagerecyclerviewdemo.bean.RightBean;
import com.parting_soul.linkagerecyclerviewdemo.utils.FileUtils;
import com.parting_soul.linkagerecyclerviewdemo.utils.ScrollTopLayoutManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Unbinder mUnbinder;

    @BindView(R.id.mRvLeft)
    RecyclerView mRvLeft;
    @BindView(R.id.mRvRight)
    RecyclerView mRvRight;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    private View mRightFooterView;

    private DataTask mDataTask;
    private List<LeftSortBean> mLeftSortBeans;
    private LeftListAdapter mLeftAdapter;

    private List<RightBean> mRightBeans;
    private RightListAdapter mRightAdapter;
    public static final int COLUMN_NUM = 3;
    private boolean isSelectLeftList;


    private static final int[] IMGS = {
            R.mipmap.a, R.mipmap.b, R.mipmap.c
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);

        mDataTask = new DataTask();
        mDataTask.execute();

        initLeftSortRecyclerView();
        initRightRecyclerView();
    }

    private void initLeftSortRecyclerView() {
        mLeftSortBeans = new ArrayList<>();
        mRvLeft.setLayoutManager(new LinearLayoutManager(this));
        mRvLeft.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        ((SimpleItemAnimator) mRvLeft.getItemAnimator()).setSupportsChangeAnimations(false);
        mLeftAdapter = new LeftListAdapter(R.layout.adapter_left_sort, mLeftSortBeans);
        mRvLeft.setAdapter(mLeftAdapter);
        mLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                isSelectLeftList = true;
                mLeftAdapter.setSelectPosition(position);

                //选中左边分类，使得右边滚动到指定位置
                LeftSortBean sortBean = mLeftSortBeans.get(position);
                int rightPosition = findRightItemPositionBySortName(sortBean.getTypeName());
                if (rightPosition != -1) {
                    mRvRight.smoothScrollToPosition(rightPosition);
                    mTvTitle.setText(sortBean.getTypeName());
                }
            }
        });
    }

    /**
     * 跟据分组名获取分组名在右侧的位置
     *
     * @param name
     * @return
     */
    private int findRightItemPositionBySortName(String name) {
        RightBean bean = null;
        for (int i = 0; i < mRightBeans.size(); i++) {
            bean = mRightBeans.get(i);
            if (TextUtils.equals(name, bean.getName())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据f分组名名获取左侧列表的位置
     *
     * @param name
     * @return
     */
    private int findLeftItemPositionBySortName(String name) {
        LeftSortBean bean = null;
        for (int i = 0; i < mLeftSortBeans.size(); i++) {
            bean = mLeftSortBeans.get(i);
            if (TextUtils.equals(name, bean.getTypeName())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取右侧列表最后一个分组白标题的位置
     *
     * @return
     */
    private int findLastGroupTitlePositionInRightLists() {
        RightBean bean = null;
        for (int i = mRightBeans.size() - 1; i >= 0; i--) {
            bean = mRightBeans.get(i);
            if (bean.getItemType() == RightBean.TYPE_TITLE) {
                return i;
            }
        }
        return -1;
    }

    private void initRightRecyclerView() {
        mRightFooterView = LayoutInflater.from(this).inflate(R.layout.footer_empty, null);
        mRightBeans = new ArrayList<>();
        mRightAdapter = new RightListAdapter(mRightBeans);
        mRvRight.setLayoutManager(new ScrollTopLayoutManager(this, COLUMN_NUM));
        mRightAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                RightBean bean = mRightBeans.get(position);
                return bean != null && bean.getItemType() == RightBean.TYPE_TITLE ? COLUMN_NUM : 1;
            }
        });
        mRvRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isSelectLeftList = false;
                    changeLeftListSelectState();

                    LinearLayoutManager manager = (LinearLayoutManager) mRvRight.getLayoutManager();
                    int lastCompleteVisiblePosition = manager.findLastCompletelyVisibleItemPosition();
                    if (lastCompleteVisiblePosition == mRightBeans.size() - 1 && mRightAdapter.getFooterLayoutCount() == 0) {
                        //已经滑动到最后一个完全显示的item并且之前没有添加过底部填充
                        View lastItemView = manager.findViewByPosition(findLastGroupTitlePositionInRightLists());
                        if (lastItemView == null) {
                            return;
                        }
                        //设置填充高度，使得最后一个分类标题能够置顶悬浮
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, lastItemView.getTop());
                        mRightFooterView.setLayoutParams(params);
                        mRightAdapter.setFooterView(mRightFooterView);
                        mRvRight.scrollBy(0, lastItemView.getTop());
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isSelectLeftList) {
                    changeLeftListSelectState();
                }
            }
        });
        mRightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RightBean rightBean = mRightBeans.get(position);
                Toast.makeText(getApplicationContext(), rightBean.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        mRvRight.setAdapter(mRightAdapter);
    }


    /**
     * 改变左侧列表选中状态
     */
    private void changeLeftListSelectState() {
        LinearLayoutManager manager = (LinearLayoutManager) mRvRight.getLayoutManager();
        int firstVisiblePosition = manager.findFirstVisibleItemPosition();
        RightBean bean = mRightAdapter.getItem(firstVisiblePosition);
        if (bean != null) {
            mLeftAdapter.setSelectPosition(findLeftItemPositionBySortName(bean.getGroupName()));
            mRvLeft.smoothScrollToPosition(firstVisiblePosition);
            if (isChangeGroup(firstVisiblePosition)) {
                mTvTitle.setText(bean.getGroupName());
            }
        }
    }

    private boolean isChangeGroup(int position) {
        GridLayoutManager manager = (GridLayoutManager) mRvRight.getLayoutManager();
        RightBean bean = mRightAdapter.getItem(position);
        int column = manager.getSpanCount();
        boolean isChanged = false;
        int dataSize = mRightBeans.size();

        //右侧列表往下滑动时，若第一个可见的是标题之前的item列表，表示可以改变分组名
        if (bean != null && bean.getItemType() == RightBean.TYPE_ITEM) {
            for (int i = 1; i <= column; i++) {
                if (position + i < dataSize) {
                    isChanged = isChanged || mRightBeans.get(position + i).getItemType() == RightBean.TYPE_TITLE;
                }
            }
        } else if (bean != null && bean.getItemType() == RightBean.TYPE_TITLE) {
            //从下往上滑动，第一个可见的是标题
            isChanged = true;
        }

        return isChanged;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDataTask.cancel(true);
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    class DataTask extends AsyncTask<Void, Void, List<DataBean>> {
        Random random = new Random();

        @Override
        protected List<DataBean> doInBackground(Void... voids) {
            Gson gson = new Gson();
            String result = FileUtils.readFromAsset(getApplicationContext(), "data.json");
            Type type = new TypeToken<List<DataBean>>() {
            }.getType();
            return gson.fromJson(result, type);
        }

        @Override
        protected void onPostExecute(List<DataBean> dataBeans) {
            if (dataBeans == null) {
                return;
            }

            for (DataBean dataBean : dataBeans) {
                mLeftSortBeans.add(new LeftSortBean(dataBean.getName()));
                mRightBeans.add(new RightBean(dataBean.getName()));
                if (dataBean.getList() == null) {
                    continue;
                }

                for (DataBean.ListBean listBean : dataBean.getList()) {
                    mRightBeans.add(new RightBean(listBean.getName(), listBean.getId(), IMGS[random.nextInt(IMGS.length)], dataBean.getName()));
                }
            }

            mLeftAdapter.notifyDataSetChanged();
            mRightAdapter.notifyDataSetChanged();
            mTvTitle.setText(mLeftSortBeans.get(0).getTypeName());
        }

    }
}

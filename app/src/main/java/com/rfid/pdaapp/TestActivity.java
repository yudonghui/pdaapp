package com.rfid.pdaapp;

import android.os.Handler;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.SpaceItemDecoration;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.entitys.TestEntity;
import com.rfid.pdaapp.utils.LogUtils;
import com.rfid.pdaapp.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

public class TestActivity extends BaseActivity {


    @BindView(R.id.recycler_view1)
    RecyclerView recyclerView1;
    @BindView(R.id.recycler_view2)
    RecyclerView recyclerView2;
    @BindView(R.id.recycler_view3)
    RecyclerView recyclerView3;
    @BindView(R.id.recycler_view4)
    RecyclerView recyclerView4;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;
    @BindView(R.id.swiperereshlayout)
    SwipeRefreshLayout swiperereshlayout;
    private CommonAdapter<TestEntity> mHomeAdapter1;
    private CommonAdapter<TestEntity> mHomeAdapter2;
    private CommonAdapter<TestEntity> mHomeAdapter3;
    private CommonAdapter<TestEntity> mHomeAdapter4;
    private ArrayList<TestEntity> mHomeList1 = new ArrayList<>();
    private ArrayList<TestEntity> mHomeList2 = new ArrayList<>();
    private ArrayList<TestEntity> mHomeList3 = new ArrayList<>();
    private ArrayList<TestEntity> mHomeList4 = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void init() {
        refreshListener();
        scrollListener();
        initAdapter();
        initData();
    }

    private void refreshListener() {
        swiperereshlayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.color_theme),
                ContextCompat.getColor(mContext, R.color.colorPrimaryDark),
                ContextCompat.getColor(mContext, R.color.color_f27070),
                ContextCompat.getColor(mContext, R.color.color_orange));
        swiperereshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        if (swiperereshlayout.isRefreshing())//正在刷新
                            swiperereshlayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });
    }

    private void scrollListener() {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LogUtils.e("滑动scrollX:" + scrollX + "   scrollY:" + scrollY + "   oldScrollX" + oldScrollX + "    oldScrollY" + oldScrollY);
            }
        });
    }

    private void initAdapter() {
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        recyclerView1.addItemDecoration(new SpaceItemDecoration(10));
        mHomeAdapter1 = new CommonAdapter<TestEntity>(mContext, R.layout.item_home, mHomeList1) {

            @Override
            protected void convert(ViewHolder holder, TestEntity homeEntity, int position) {
                holder.setText(R.id.tv_content, Strings.getString(homeEntity.getContent()));
            }
        };
        recyclerView1.setAdapter(mHomeAdapter1);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView2.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(mContext, R.color.transparent)));
        mHomeAdapter2 = new CommonAdapter<TestEntity>(mContext, R.layout.item_home, mHomeList2) {

            @Override
            protected void convert(ViewHolder holder, TestEntity homeEntity, int position) {
                holder.setText(R.id.tv_content, Strings.getString(homeEntity.getContent()));
            }
        };
        recyclerView2.setAdapter(mHomeAdapter2);

        GridLayoutManager gridLayoutManager3 = new GridLayoutManager(mContext, 2);
        recyclerView3.setLayoutManager(gridLayoutManager3);
        recyclerView3.addItemDecoration(new SpaceItemDecoration(10, 10));
        mHomeAdapter3 = new CommonAdapter<TestEntity>(mContext, R.layout.item_home, mHomeList3) {

            @Override
            protected void convert(ViewHolder holder, TestEntity homeEntity, int position) {
                holder.setText(R.id.tv_content, Strings.getString(homeEntity.getContent()));
            }
        };
        recyclerView3.setAdapter(mHomeAdapter3);

        GridLayoutManager gridLayoutManager4 = new GridLayoutManager(mContext, 2);
        recyclerView4.setLayoutManager(gridLayoutManager4);
        recyclerView4.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.HORIZONTAL, 10, ContextCompat.getColor(mContext, R.color.transparent)));
        mHomeAdapter4 = new CommonAdapter<TestEntity>(mContext, R.layout.item_home, mHomeList4) {
            @Override
            protected void convert(ViewHolder holder, TestEntity homeEntity, int position) {
                holder.setText(R.id.tv_content, Strings.getString(homeEntity.getContent()));
            }
        };
        recyclerView4.setAdapter(mHomeAdapter4);
    }

    private void initData() {
        mHomeList1.add(new TestEntity("预约管理"));
        mHomeList1.add(new TestEntity("叫号管理"));
        mHomeList1.add(new TestEntity("收费单"));
        mHomeList1.add(new TestEntity("收费单2"));
        mHomeList2.add(new TestEntity("建档"));
        mHomeList2.add(new TestEntity("医生查询"));
        mHomeList2.add(new TestEntity("住院查询"));
        mHomeList2.add(new TestEntity("住院查询2"));
        mHomeList3.add(new TestEntity("收费单"));
        mHomeList3.add(new TestEntity("预约管理"));
        mHomeList3.add(new TestEntity("叫号管理"));
        mHomeList3.add(new TestEntity("叫号管理2"));
        mHomeList4.add(new TestEntity("sfsadfsdf"));
        mHomeList4.add(new TestEntity("asdfsdf"));
        mHomeList4.add(new TestEntity("美莱电商券"));
        mHomeList4.add(new TestEntity("电商券"));
        mHomeAdapter1.notifyDataSetChanged();
        mHomeAdapter2.notifyDataSetChanged();
        mHomeAdapter3.notifyDataSetChanged();
        mHomeAdapter4.notifyDataSetChanged();
    }

}

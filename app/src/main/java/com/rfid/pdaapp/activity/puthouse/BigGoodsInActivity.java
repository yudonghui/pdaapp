package com.rfid.pdaapp.activity.puthouse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.adapters.BigGoodsAdapter;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.views.ErrorView;
import com.rfid.pdaapp.views.TitleSearchBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by ydh on 2022/1/12
 * 大货退货入库
 */
public class BigGoodsInActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleSearchBar tvTitle;
    @BindView(R.id.error_view)
    ErrorView errorView;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private BigGoodsAdapter mBigGoodsAdapter;
    List<Map<String, Object>> mDataList = new ArrayList<>();
    private String[] mFieldKeys = {"FID", "FMATERIALID", "FMaterialId.FNumber", "FMATERIALNAME", "FMODEL"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_big_goods_in;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initAdapter();
        initListener();
        refreshLayout.autoRefresh();
    }

    private void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
    }

    private void initAdapter() {
        mBigGoodsAdapter = new BigGoodsAdapter(mContext, R.layout.item_big_goods, mDataList);
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mBigGoodsAdapter);
    }

    private void initData() {
        mDataList.clear();
        for (int i = 0; i < 15; i++) {
            mDataList.add(new HashMap<>());
        }
        mBigGoodsAdapter.notifyDataSetChanged();
        stopRefresh(refreshLayout);
    }

}

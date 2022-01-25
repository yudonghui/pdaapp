package com.rfid.pdaapp.activity.inventory;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.views.ErrorView;
import com.rfid.pdaapp.views.TitleSearchBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by ydh on 2022/1/24
 */
public class InventoryProductActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleSearchBar tvTitle;
    @BindView(R.id.error_view)
    ErrorView errorView;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    List<Map<String, Object>> mDataList = new ArrayList<>();
    private CommonAdapter<Map<String, Object>> mCommonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inventory_product;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initAdapter();
        initListener();
        initData();
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
        mCommonAdapter = new CommonAdapter<Map<String, Object>>(mContext, R.layout.item_inventory, mDataList) {

            @Override
            protected void convert(ViewHolder holder, Map<String, Object> stringObjectMap, int position) {
                holder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(InventoryFormActivity.class);
                    }
                });
            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mCommonAdapter);
    }

    private void initData() {
        stopRefresh(refreshLayout);
        mDataList.clear();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<>();
            mDataList.add(map);
        }
        mCommonAdapter.notifyDataSetChanged();
    }

}
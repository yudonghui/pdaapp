package com.rfid.pdaapp.activity.inventory;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ydh on 2022/1/25
 */
public class HistoryStockActivity extends BaseActivity {

    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.tv_near)
    TextView tvNear;
    List<Map<String, Object>> mDataList = new ArrayList<>();
    private CommonAdapter<Map<String, Object>> mCommonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history_stock;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initAdapter();
        initData();
    }


    @OnClick(R.id.tv_near)
    public void onViewClicked() {
        tvNear.setVisibility(View.GONE);
        initNearData();
    }


    private void initAdapter() {
        mCommonAdapter = new CommonAdapter<Map<String, Object>>(mContext, R.layout.item_history_stock, mDataList) {

            @Override
            protected void convert(ViewHolder holder, Map<String, Object> stringObjectMap, int position) {

            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mCommonAdapter);
    }

    private void initData() {
        mDataList.clear();
        for (int i = 0; i < 2; i++) {
            mDataList.add(new HashMap<>());
        }
        mCommonAdapter.notifyDataSetChanged();
    }

    private void initNearData() {
        for (int i = 0; i < 10; i++) {
            mDataList.add(new HashMap<>());
        }
        mCommonAdapter.notifyDataSetChanged();
    }

}
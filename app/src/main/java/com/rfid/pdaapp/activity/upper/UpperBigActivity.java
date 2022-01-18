package com.rfid.pdaapp.activity.upper;

import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.views.ErrorView;
import com.rfid.pdaapp.views.TitleSearchBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class UpperBigActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleSearchBar tvTitle;
    @BindView(R.id.error_view)
    ErrorView errorView;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    List<Map<String, Object>> mDataList = new ArrayList<>();
    private CommonAdapter<Map<String, Object>> mCommmonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upper_big;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initAdapter();
        initListener();
        initData();
    }

    private void initListener() {
        tvTitle.setListener(new TitleSearchBar.TextListener() {
            @Override
            public void onClick(TextView textView) {

            }
        });
        tvTitle.addTextChangedListener(new TitleSearchBar.EditeListener() {
            @Override
            public void afterTextChanged(String s) {

            }
        });
    }

    private void initAdapter() {
        mCommmonAdapter = new CommonAdapter<Map<String, Object>>(mContext, R.layout.item_upper_big, mDataList) {

            @Override
            protected void convert(ViewHolder holder, Map<String, Object> stringObjectMap, int position) {

            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mCommmonAdapter);
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> e = new HashMap<>();
            mDataList.add(e);
        }
        mCommmonAdapter.notifyDataSetChanged();
    }
}
package com.rfid.pdaapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.adapters.UpperAdapter;
import com.rfid.pdaapp.common.base.BaseFragment;
import com.rfid.pdaapp.views.ErrorView;
import com.rfid.pdaapp.views.SListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class UpperFragment extends BaseFragment {

    @BindView(R.id.error_view)
    ErrorView errorView;
    @BindView(R.id.lv_data)
    SListView lvData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int type;
    private int meOrOther;//0我的，1其他
    private UpperAdapter mUpperAdapter;
    List<Map<String, Object>> mDataList = new ArrayList<>();

    public static UpperFragment newInstance(int type, int meOrOther) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("meOrOther", meOrOther);
        UpperFragment fragment = new UpperFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upper;
    }

    @Override
    protected void init() {
        type = getArguments().getInt("type");
        meOrOther = getArguments().getInt("meOrOther");
        initAdapter();
        initListener();
        initData();
    }

    private void initAdapter() {
        mUpperAdapter = new UpperAdapter(mContext, R.layout.item_upper, mDataList,type);
        lvData.setAdapter(mUpperAdapter);
    }

    private void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });

    }

    private void initData() {
        mDataList.clear();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<>();
            mDataList.add(map);
        }
        stopOver(refreshLayout);
        mUpperAdapter.notifyDataSetChanged();
    }
}

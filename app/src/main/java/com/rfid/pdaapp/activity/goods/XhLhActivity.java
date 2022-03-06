package com.rfid.pdaapp.activity.goods;

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

public class XhLhActivity extends BaseActivity {

    @BindView(R.id.error_view)
    ErrorView errorView;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private CommonAdapter<Map<String, Object>> mAdapter;
    List<Map<String, Object>> mDataList = new ArrayList<>();
    private String FID;
    private String FBillNo;
    private String F_HFL_SCH_FNAME;
    private String F_HFL_SCH_FNUMBER;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xh_lh;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        FID = getIntent().getStringExtra("FID");
        FBillNo = getIntent().getStringExtra("FBillNo");
        F_HFL_SCH_FNAME = getIntent().getStringExtra("F_HFL_SCH.FNAME");
        F_HFL_SCH_FNUMBER = getIntent().getStringExtra("F_HFL_SCH.FNUMBER ");
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
        mAdapter = new CommonAdapter<Map<String, Object>>(mContext, R.layout.item_xh_lh, mDataList) {

            @Override
            protected void convert(ViewHolder viewHolder, Map<String, Object> stringObjectMap, int i) {
                viewHolder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("FBillNo", FBillNo);
                        startActivity(CbLhActivity.class, bundle);
                    }
                });
            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.HORIZONTAL, 10, ContextCompat.getColor(mContext, R.color.transparent)));
        rvData.setAdapter(mAdapter);
    }

    private void initData() {
        stopRefresh(refreshLayout);
        mDataList.clear();
        mDataList.add(new HashMap<>());
        mDataList.add(new HashMap<>());
        mDataList.add(new HashMap<>());
        mDataList.add(new HashMap<>());
        mDataList.add(new HashMap<>());
        mAdapter.notifyDataSetChanged();
    }

}
package com.rfid.pdaapp.activity.puthouse;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.adapters.BigGoodsAdapter;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.utils.Strings;
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
import butterknife.OnClick;

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
    @BindView(R.id.tv_add)
    TextView tvAdd;
    private BigGoodsAdapter mBigGoodsAdapter;
    List<Map<String, Object>> mDataList = new ArrayList<>();
    private String[] mFieldKeys = {"tv_goods_code", "tv_goods_name", "tv_bill_num", "tv_time", "tv_operator", "tv_receive_time", "status"};

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

    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        startActivity(CreatInStockActivity.class);
    }

    private void initAdapter() {
        mBigGoodsAdapter = new BigGoodsAdapter(mContext, R.layout.item_big_goods, mDataList);
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mBigGoodsAdapter);
    }

    private void initData() {
        String strByArray = Strings.getStrByArray(mFieldKeys);
        mDataList.clear();
        for (int i = 0; i < 15; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("tv_goods_code", "RK21221123321");
            map.put("tv_goods_name", "上海市闵行区九号线上海大学  销售单号：");
            map.put("tv_bill_num", "XD5218625221");
            map.put("tv_time", "2021-12-30 15:12:30");
            map.put("tv_operator", "test01");
            map.put("tv_receive_time", "2021-12-30 15:12:30");
            map.put("status", 1 + i);
            mDataList.add(map);
        }
        mBigGoodsAdapter.notifyDataSetChanged();
        stopRefresh(refreshLayout);
    }

}

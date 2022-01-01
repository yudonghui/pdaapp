package com.rfid.pdaapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.common.network.HttpClient;
import com.rfid.pdaapp.utils.CommonUtil;
import com.rfid.pdaapp.utils.Strings;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockLocationActivity extends BaseActivity {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_box_num)
    TextView tvBoxNum;
    @BindView(R.id.tv_product_num)
    TextView tvProductNum;
    @BindView(R.id.tv_continue)
    TextView tvContinue;
    @BindView(R.id.tv_back_main)
    TextView tvBackMain;
    @BindView(R.id.rv_stock)
    RecyclerView rvStock;
    @BindView(R.id.no_data)
    TextView noData;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;
    private String[] mFieldKeys = {"FID", "FMATERIALID", "FMaterialId.FNumber", "FMATERIALNAME", "FMODEL", "FAUXPROPID.FF100001.FNUMBER",
            "FAUXPROPID.FF100001.FNAME", "FAUXPROPID.FF100004.FNUMBER", "FAUXPROPID.FF100004.FNAME", "FSTOCKNAME", "FLOT.FNUMBER",
            "FSTOCKUNITID.FNAME", "FQTY", "FSTOCKORGID.FNAME", "FOWNERID.FNUMBER", "FOWNERNAME"};
    private String fMaterialId_fNumber;
    private List<Map<String, Object>> mLocationList = new ArrayList<>();
    private CommonAdapter<Map<String, Object>> mLocationAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_location;
    }

    private int StartRow = 0;
    private int Limit = 10;

    @Override
    protected void init() {
        fMaterialId_fNumber = getIntent().getStringExtra("FMaterialId_FNumber");
        initAdapter();
        initData();
        initListener();
    }

    @OnClick({R.id.tv_continue, R.id.tv_back_main})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_continue:
                finish();
                break;
            case R.id.tv_back_main:
                startActivity(MainActivity.class);
                break;
        }
    }

    private void initListener() {
        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                StartRow = 0;
                initData();
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                StartRow += Limit;
                initData();
            }
        });
    }

    private void initAdapter() {
        mLocationAdapter = new CommonAdapter<Map<String, Object>>(mContext, R.layout.item_stock_location, mLocationList) {

            @Override
            protected void convert(ViewHolder holder, Map<String, Object> stringObjectMap, int position) {
                holder.setText(R.id.tv_fmaterialid_fnumber, Strings.getStringL(stringObjectMap.get("FMaterialId.FNumber")));
                holder.setText(R.id.tv_fmaterialname, Strings.getStringL(stringObjectMap.get("FMATERIALNAME")));
              //  holder.setText(R.id.tv_fmodel, Strings.getStringL(stringObjectMap.get("FMODEL")));
                holder.setText(R.id.tv_fauxpropid_ff100004_fname, Strings.getStringL(stringObjectMap.get("FAUXPROPID.FF100004.FNAME")));
            }

        };
        rvStock.setLayoutManager(new LinearLayoutManager(mContext));
        // rvStock.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvStock.setAdapter(mLocationAdapter);
    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("FormId", "STK_Inventory");
        map.put("Limit", Limit);
        map.put("StartRow", StartRow);
        map.put("FilterString", "FMaterialId.FNumber='" + fMaterialId_fNumber + "'");
        // map.put("FilterString", "FMaterialId.FNumber='" + fMaterialId_fNumber + "'");
        map.put("OrderString", "FID ASC");
        map.put("FieldKeys", Strings.getStrByArray(mFieldKeys));
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", map);
        Call<List<List<Object>>> call = HttpClient.getHttpApi().locationForm(HttpClient.getRequestBody(paramMap));
        mNetWorkList.add(call);
        call.enqueue(new Callback<List<List<Object>>>() {
            @Override
            public void onResponse(Call<List<List<Object>>> call, Response<List<List<Object>>> response) {
                stopRefresh(refreshlayout);
                if (response != null && response.isSuccessful() && response.body() != null) {
                    List<List<Object>> body = response.body();
                    if (body == null || body.size() == 0) {
                        CommonUtil.showToast("无数据");
                        return;
                    }
                    if (StartRow == 0) {
                        mLocationList.clear();
                    }
                    mLocationList.addAll(Strings.getListMap(body, mFieldKeys));
                    mLocationAdapter.notifyDataSetChanged();
                } else {
                    CommonUtil.showToast("获取库位失败");
                }
            }

            @Override
            public void onFailure(Call<List<List<Object>>> call, Throwable t) {
                stopRefresh(refreshlayout);
                CommonUtil.showToast("获取库位失败");
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
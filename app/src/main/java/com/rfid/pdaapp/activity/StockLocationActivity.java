package com.rfid.pdaapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.common.network.HttpClient;
import com.rfid.pdaapp.entitys.ErrorEntity;
import com.rfid.pdaapp.utils.CommonUtil;
import com.rfid.pdaapp.utils.Strings;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockLocationActivity extends BaseActivity {

    @BindView(R.id.tv_code_title)
    TextView tvCodeTitle;
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
    /**
     * 物料编码:FMaterialId.FNumber 		产品
     * 物料名称:FMATERIALNAME
     * 规格型号:FMODEL
     * 学校.编码:FAUXPROPID.FF100001.FNUMBER
     * 学校.名称:FAUXPROPID.FF100001.FNAME
     * 尺码.编码:AUXPROPID.FF100004.FNUMBER
     * 尺码.名称:FAUXPROPID.FF100004.FNAME
     * 仓库名称:FSTOCKNAME
     * 批号:FLOT.FNUMBER
     * 库存主单位:FSTOCKUNITID.FNAME
     * 库存量(主单位):FQTY
     * 可用量：FAVBQTY
     * 库存组织:FSTOCKORGID.FNAME
     * 货主编码:FOWNERID.FNUMBER
     * 货主名称:FOWNERNAME
     * 仓位：FSTOCKLOCID
     */
    private String[] mFieldKeys = {"FID", "FMATERIALID", "FMaterialId.FNumber", "FMATERIALNAME", "FMODEL", "FAUXPROPID.FF100001.FNUMBER",
            "FAUXPROPID.FF100001.FNAME", "FAUXPROPID.FF100004.FNUMBER", "FAUXPROPID.FF100004.FNAME", "FSTOCKNAME", "FLOT.FNUMBER",
            "FSTOCKUNITID.FNAME", "FAVBQTY", "FQTY", "FSTOCKORGID.FNAME", "FOWNERID.FNUMBER", "FOWNERNAME", "FSTOCKLOCID"};
    private String code;
    private List<Map<String, Object>> mLocationList = new ArrayList<>();
    private CommonAdapter<Map<String, Object>> mLocationAdapter;
    private int type;//0 产品，1库位，3箱号

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_location;
    }

    private int StartRow = 0;
    private int Limit = 10;

    @Override
    protected void init() {
        type = getIntent().getIntExtra("type", 0);
        code = getIntent().getStringExtra("code");
        tvCode.setText(Strings.getStringL(code));
        if (type == 0) {
            tvCodeTitle.setText("产品编码");
        } else if (type == 1) {
            tvCodeTitle.setText("库位编码");
        } else {
            tvCodeTitle.setText("箱子编码");
        }
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
                holder.setText(R.id.tv_fmaterialid_fnumber, Strings.getStringL(stringObjectMap.get("FMaterialId.FNumber")));//物料编码
                holder.setText(R.id.tv_fmaterialname, Strings.getStringL(stringObjectMap.get("FMATERIALNAME")));//物料名称
                holder.setText(R.id.tv_fauxpropid_ff100001_fname, Strings.getStringL(stringObjectMap.get("FAUXPROPID.FF100001.FNAME")));//学校.名称
                holder.setText(R.id.tv_fauxpropid_ff100004_fname, Strings.getStringL(stringObjectMap.get("FAUXPROPID.FF100004.FNAME")));//尺码.名称
                holder.setText(R.id.tv_favbqty, Strings.getStringL(stringObjectMap.get("FAVBQTY")));//可用
                holder.setText(R.id.tv_fqty, Strings.getStringL(stringObjectMap.get("FQTY")));//实存
                holder.setText(R.id.tv_box_no, "-");//箱号
                holder.setText(R.id.tv_fstocklocid, Strings.getStringL(stringObjectMap.get("FSTOCKLOCID")));//库位（仓位）

            }

        };
        rvStock.setLayoutManager(new LinearLayoutManager(mContext));
        // rvStock.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvStock.setAdapter(mLocationAdapter);
    }

    /*private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("FormId", "STK_Inventory");
        map.put("Limit", Limit);
        map.put("StartRow", StartRow);
        if (type == 0) {//产品
            map.put("FilterString", "FMaterialId.FNumber='" + code + "'");
        } else if (type == 1) {//库位
            map.put("FilterString", "FSTOCKLOCID='" + code + "'");
        } else {//箱号

        }
        map.put("FilterString", "FMaterialId.FNumber='" + code + "'");
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
                    if (body == null) {
                        return;
                    }
                    if (body.size() == 0 && StartRow > 0) {
                        CommonUtil.showToast("无更多数据");
                        return;
                    }
                    if (body.size() > 0 && body.get(0).size() > 0) {
                        Object o = body.get(0).get(0);
                        if (!(o instanceof String)) {
                            CommonUtil.showToast("系统错误");
                            return;
                        }
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
*/
    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("FormId", "STK_Inventory");
        map.put("Limit", Limit);
        map.put("StartRow", StartRow);
        map.put("FilterString", "FMaterialId.FNumber='" + code + "'");
        // map.put("FilterString", "FMaterialId.FNumber='" + fMaterialId_fNumber + "'");
        map.put("OrderString", "FID ASC");
        map.put("FieldKeys", Strings.getStrByArray(mFieldKeys));
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", map);
        Call<ResponseBody> call = HttpClient.getHttpApi().locationFormStr(HttpClient.getRequestBody(paramMap));
        mNetWorkList.add(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                stopRefresh(refreshlayout);
                if (response != null && response.isSuccessful() && response.body() != null) {
                    try {

                        String body = response.body().string();
                        if (body.startsWith("[[{")) {//网络请求失败
                            errorHandl(body);
                        } else {
                            List<List<Object>> o = new Gson().fromJson(body, new TypeToken<List<List<Object>>>() {
                            }.getType());
                            if (o == null) {
                                return;
                            }
                            if (o.size() == 0 && StartRow > 0) {
                                CommonUtil.showToast("无更多数据");
                                return;
                            }

                            if (StartRow == 0) {
                                mLocationList.clear();
                            }
                            mLocationList.addAll(Strings.getListMap(o, mFieldKeys));
                            mLocationAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    CommonUtil.showToast("获取库位失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


}
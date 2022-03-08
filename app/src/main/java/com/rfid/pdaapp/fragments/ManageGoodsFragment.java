package com.rfid.pdaapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rfid.pdaapp.R;
import com.rfid.pdaapp.adapters.ManageGoodsAdapter;
import com.rfid.pdaapp.callback.PdaTwoInterface;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.SPUtils;
import com.rfid.pdaapp.common.base.BaseFragment;
import com.rfid.pdaapp.common.network.HttpClient;
import com.rfid.pdaapp.utils.CommonUtil;
import com.rfid.pdaapp.utils.Strings;
import com.rfid.pdaapp.views.ErrorView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageGoodsFragment extends BaseFragment {

    @BindView(R.id.error_view)
    ErrorView errorView;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int meOrOther;
    private ManageGoodsAdapter mAdapter;
    private List<Map<String, Object>> mDataList = new ArrayList<>();

    private int StartRow = 0;
    private int Limit = 10;
    /**
     * FID: 单据ID
     * FBillNo: 单据编号
     * FCREATEDATE: 创建时间
     * F_HFL_SCH.FNAME: 学校名称
     * F_HFL_SCH.FNUMBER: 学校编码
     * FMATERIALID.FNUMBER: 物料编码
     * FMATERIALNAME: 物料名称
     * FMATERIALID.FNAME: 物料名称(与FMATERIALNAME相同)
     * FQTY: 数量
     * FUNITID.FNAME 单位名称
     **/
    private String[] mFieldKeys = {"FID", "FBillNo", "FCREATEDATE", "F_HFL_SCH.FNAME", "F_HFL_SCH.FNUMBER"};

    public static ManageGoodsFragment newInstance(int meOrOther) {
        Bundle bundle = new Bundle();
        bundle.putInt("meOrOther", meOrOther);
        ManageGoodsFragment fragment = new ManageGoodsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manage_goods;
    }

    @Override
    protected void init() {
        meOrOther = getArguments().getInt("meOrOther");
        initAdapter();
        initListener();
        initData();
    }

    private void initAdapter() {
        mAdapter = new ManageGoodsAdapter(mContext, R.layout.item_manage_goods, mDataList,meOrOther);
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mAdapter);
        mAdapter.setListener(new PdaTwoInterface() {
            @Override
            public void clickOne(int position) {

            }

            @Override
            public void clickTwo(int position) {

            }
        });
    }

    private void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                StartRow = 0;
                initData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                StartRow += Limit;
                initData();
            }
        });

    }


    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("FormId", "STK_TRANSFERAPPLY");
        map.put("Limit", Limit);
        map.put("StartRow", StartRow);
        map.put("FilterString", "FBillTypeID.FNUMBER='DBSQD11_SYS' and F_BRE_ZDRUserId='" +
                (meOrOther == 0 ? SPUtils.getCache(SPUtils.FILE_USER, SPUtils.USER_ID) : "") + "'");
        map.put("OrderString", "");
        map.put("FieldKeys", Strings.getStrByArray(mFieldKeys));
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", map);
        Call<ResponseBody> call = HttpClient.getHttpApi().manageGoodsForm(HttpClient.getRequestBody(paramMap));
        mNetWorkList.add(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                stopRefresh(refreshLayout);
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
                                mDataList.clear();
                            }
                            mDataList.addAll(Strings.getListMap(o, mFieldKeys));
                            mAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    CommonUtil.showToast("获取库位失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });
    }
}

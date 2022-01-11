package com.rfid.pdaapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.rfid.pdaapp.R;
import com.rfid.pdaapp.adapters.StockIndexAdapter;
import com.rfid.pdaapp.callback.PositionInerface;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.common.network.HttpClient;
import com.rfid.pdaapp.entitys.StockIndexEntity;
import com.rfid.pdaapp.utils.CommonUtil;
import com.rfid.pdaapp.utils.Strings;
import com.rfid.pdaapp.views.TitleSearchBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ydh on 2022/1/11
 * 仓库列表
 */
public class StockIndexActivity extends BaseActivity {

    @BindView(R.id.rv_stock)
    RecyclerView rvStock;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    @BindView(R.id.tv_title)
    TitleSearchBar tvTitle;
    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;
    private List<StockIndexEntity> mDataList = new ArrayList<>();//列表数据
    private List<StockIndexEntity> mDataAllList = new ArrayList<>();//列表数据
    private StockIndexAdapter mStockAdapter;
    /**
     * FStockId：仓库id
     * FNumber： 仓库编码
     * FName：   仓库名称
     */
    private String[] mFieldKeys = {"FStockId", "FNumber", "FName"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_index;
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
        tvTitle.setListener(new TitleSearchBar.TextListener() {
            @Override
            public void onClick(TextView textView) {
                searchData();
            }
        });
        tvTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_SEND) {
                    InputMethodManager imm = (InputMethodManager) v.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    searchData();
                    return true;
                }
                return false;
            }
        });
    }

    private void searchData() {
        String text = tvTitle.getText();
        mDataList.clear();
        if (TextUtils.isEmpty(text)) {
            mDataList.addAll(mDataAllList);
        } else {
            for (int i = 0; i < mDataAllList.size(); i++) {
                String fName = mDataAllList.get(i).getFName();
                if (fName.contains(text)) {
                    mDataList.add(mDataAllList.get(i));
                }
            }
        }
        mStockAdapter.notifyDataSetChanged();
        indexBar.setmSourceDatas(mDataList).invalidate();
        mDecoration.setmDatas(mDataList);
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initAdapter() {
        rvStock.setLayoutManager(mManager = new LinearLayoutManager(mContext));
        mDecoration = new SuspensionDecoration(mContext, mDataList);
        mDecoration.setColorTitleBg(Color.parseColor("#00000000"));
        mDecoration.setColorTitleFont(ContextCompat.getColor(mContext, R.color.color_theme));
        rvStock.addItemDecoration(mDecoration);
        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        mStockAdapter = new StockIndexAdapter(mContext, R.layout.item_stock_index, mDataList, new PositionInerface() {

            @Override
            public void onClick(int position) {
                Intent data = new Intent();
                data.putExtra("FStockId", mDataList.get(position).getFStockId());
                data.putExtra("FNumber", mDataList.get(position).getFNumber());
                data.putExtra("FName", mDataList.get(position).getFName());
                setResult(Constant.RESULT_CODE0, data);
                finish();
            }
        });
        rvStock.setAdapter(mStockAdapter);
    }

    private void initData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("FormId", "BD_STOCK");
        map.put("FieldKeys", Strings.getStrByArray(mFieldKeys));
        map.put("FilterString", "");
        map.put("OrderString", "");
        map.put("TopRowCount", 0);
        map.put("StartRow", 0);
        map.put("Limit", 0);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", map);
        Call<ResponseBody> call = HttpClient.getHttpApi().locationFormStr(HttpClient.getRequestBody(paramMap));
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

                            mDataAllList.clear();
                            mDataList.clear();
                            for (int i = 0; i < o.size(); i++) {
                                List<Object> objects = o.get(i);
                                if (objects.size() == 3) {
                                    StockIndexEntity stockIndexEntity = new StockIndexEntity(Strings.getString(objects.get(0)),
                                            Strings.getString(objects.get(1)),
                                            Strings.getString(objects.get(2)));
                                    mDataAllList.add(stockIndexEntity);
                                }
                            }
                            mDataList.addAll(mDataAllList);
                            tvTitle.setHint("共" + mDataList.size() + "条");
                            mStockAdapter.notifyDataSetChanged();
                            indexBar.setmSourceDatas(mDataList).invalidate();
                            mDecoration.setmDatas(mDataList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    CommonUtil.showToast("获取数据失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                stopRefresh(refreshLayout);
                CommonUtil.showToast("获取数据失败");
            }
        });
    }

}
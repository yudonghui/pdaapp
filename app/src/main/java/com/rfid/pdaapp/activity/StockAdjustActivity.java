package com.rfid.pdaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.utils.Strings;
import com.rfid.pdaapp.views.TitleBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ydh on 2022/1/11
 * 库位调整
 */
public class StockAdjustActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    @BindView(R.id.tv_stock_code)
    TextView tvStockCode;
    @BindView(R.id.iv_data)
    ImageView ivData;
    @BindView(R.id.ll_product_title)
    LinearLayout llProductTitle;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.tv_box_title)
    TextView tvBoxTitle;
    @BindView(R.id.tv_original_num)
    TextView tvOriginalNum;
    @BindView(R.id.tv_adjust_num)
    TextView tvAdjustNum;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private List<Map<String, Object>> mDataList = new ArrayList<>();
    private boolean isSelect = false;//列出库位上所有产品  是否选中
    private CommonAdapter<Map<String, Object>> mCommonAdapter;
    /**
     * 产品 实存 已配数 调整数
     */
    private String[] mFieldKeys = {"productCode", "shicun", "yipeishu", "tiaozhengshu"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_adjust;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle.setListener(new TitleBar.TextListener() {
            @Override
            public void onClick(TextView textView) {
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE0);
            }
        });
        initAdapter();
    }

    @OnClick({R.id.iv_data, R.id.tv_clear, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_data:
                getData();
                break;
            case R.id.tv_clear:
                break;
            case R.id.tv_confirm:
                break;
        }
    }

    private void getData() {
        if (isSelect) {
            ivData.setImageResource(R.mipmap.unselect_more_icon);
            isSelect = false;
            mDataList.clear();
            mCommonAdapter.notifyDataSetChanged();
        } else {
            ivData.setImageResource(R.mipmap.select_more_icon);
            isSelect = true;
            initData();
        }
    }


    private void initAdapter() {
        mCommonAdapter = new CommonAdapter<Map<String, Object>>(mContext, R.layout.item_stock_adjust, mDataList) {

            @Override
            protected void convert(ViewHolder holder, Map<String, Object> stringObjectMap, int position) {
                holder.setText(R.id.tv_product_code, Strings.getStringL(stringObjectMap.get("productCode")));
                holder.setText(R.id.tv_real_num, Strings.getStringL(stringObjectMap.get("shicun")));
                holder.setText(R.id.tv_already_num, Strings.getStringL(stringObjectMap.get("yipeishu")));
                holder.setText(R.id.tv_adjust_num, Strings.getStringL(stringObjectMap.get("tiaozhengshu")));
            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.HORIZONTAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mCommonAdapter);
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("productCode", "HD200010A165");
            map.put("shicun", "5");
            map.put("yipeishu", "15");
            map.put("tiaozhengshu", "0");
            mDataList.add(map);
        }
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE0 && resultCode == Constant.RESULT_CODE0) {
            String scanResult = data.getStringExtra("scanResult");
            tvStockCode.setText(Strings.getString(scanResult));
        }
    }
}

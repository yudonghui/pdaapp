package com.rfid.pdaapp.activity.puthouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.HWScanActivity;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.utils.CommonUtil;
import com.rfid.pdaapp.views.TitleBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by ydh on 2022/1/13
 * 退库
 */
public class OutStockActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    @BindView(R.id.tv_out_stock_num)
    TextView tvOutStockNum;
    @BindView(R.id.tv_total_num)
    TextView tvTotalNum;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    List<Map<String, Object>> mDataList = new ArrayList<>();
    private CommonAdapter<Map<String, Object>> mCommonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_out_stock;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initAdapter();
        initData();
        tvTitle.setListener(new TitleBar.TextListener() {
            @Override
            public void onClick(TextView textView) {
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE0);
            }
        });
    }

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter<Map<String, Object>>(mContext, R.layout.item_out_stock, mDataList) {

            @Override
            protected void convert(ViewHolder holder, Map<String, Object> stringObjectMap, int position) {
                holder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(OutBoxActivity.class);
                    }
                });
            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mCommonAdapter);
    }

    private void initData() {
        mDataList.clear();
        for (int i = 0; i < 10; i++) {
            mDataList.add(new HashMap<>());
        }
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE0 && resultCode == Constant.RESULT_CODE0) {
            String scanResult = data.getStringExtra("scanResult");
            CommonUtil.showToast(scanResult + "");
        }
    }
}

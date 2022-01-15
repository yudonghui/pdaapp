package com.rfid.pdaapp.activity.puthouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
import butterknife.OnClick;

/**
 * Created by ydh on 2022/1/15
 * 退库
 */
public class OutBoxActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    @BindView(R.id.tv_out_stock_num)
    TextView tvOutStockNum;
    @BindView(R.id.ll_box_title)
    LinearLayout llBoxTitle;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.tv_product_num)
    TextView tvProductNum;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    List<Map<String, Object>> mDataList = new ArrayList<>();
    private CommonAdapter<Map<String, Object>> mCommonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_out_box;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initAdapter();
        initListener();
        initData();
    }

    @OnClick({R.id.tv_clear, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear:
                break;
            case R.id.tv_confirm:
                break;
        }
    }

    private void initListener() {
        tvTitle.setListener(new TitleBar.TextListener() {
            @Override
            public void onClick(TextView textView) {
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE0);
            }
        });
    }

    private void initAdapter() {
        mCommonAdapter = new CommonAdapter<Map<String, Object>>(mContext, R.layout.item_out_box, mDataList) {

            @Override
            protected void convert(ViewHolder viewHolder, Map<String, Object> item, int position) {
                viewHolder.setText(R.id.tv_code, (String) item.get("code"));
                viewHolder.setText(R.id.tv_num, item.get("num") + "");
            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mCommonAdapter);
    }

    private void initData() {
        mDataList.clear();
        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("code", "T4567890999");
            map.put("num", i + 1);
            mDataList.add(map);
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
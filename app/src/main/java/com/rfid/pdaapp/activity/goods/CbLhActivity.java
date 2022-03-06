package com.rfid.pdaapp.activity.goods;

import android.content.Intent;
import android.os.Bundle;
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
import com.rfid.pdaapp.entitys.StockGoodsEntity;
import com.rfid.pdaapp.utils.Strings;
import com.rfid.pdaapp.views.TitleBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CbLhActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    @BindView(R.id.tv_fbillno)
    TextView tvFbillno;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.tv_require)
    TextView tvRequire;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    private List<StockGoodsEntity> mDataList = new ArrayList<>();
    private CommonAdapter<StockGoodsEntity> mAdapter;
    private String FBillNo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cb_lh;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        FBillNo = getIntent().getStringExtra("FBillNo");
        tvFbillno.setText(Strings.getString(FBillNo));
        initListener();
        initAdapter();
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
        mAdapter = new CommonAdapter<StockGoodsEntity>(mContext, R.layout.item_cb_lh, mDataList) {

            @Override
            protected void convert(ViewHolder viewHolder, StockGoodsEntity stockGoodsEntity, int i) {

            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.HORIZONTAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE0 && resultCode == Constant.RESULT_CODE0) {
            String scanResult = data.getStringExtra("scanResult");
            mDataList.add(new StockGoodsEntity(scanResult));
            mAdapter.notifyDataSetChanged();
        }
    }
}
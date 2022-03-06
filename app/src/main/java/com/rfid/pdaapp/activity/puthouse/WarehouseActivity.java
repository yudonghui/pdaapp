package com.rfid.pdaapp.activity.puthouse;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.utils.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ydh on 2022/1/12
 * 收货入库
 */
public class WarehouseActivity extends BaseActivity {

    @BindView(R.id.tv_big_goods)
    TextView tvBigGoods;
    @BindView(R.id.tv_stock_put)
    TextView tvStockPut;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_warehouse;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_big_goods, R.id.tv_stock_put})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_big_goods:
                CommonUtil.showToast("开发中......");
                // startActivity(BigGoodsInActivity.class);
                break;
            case R.id.tv_stock_put:
                startActivity(StockPutActivity.class);
                break;
        }
    }

/*
    @OnClick(R.id.tv_big_goods)
    public void onViewClicked() {

    }*/
}

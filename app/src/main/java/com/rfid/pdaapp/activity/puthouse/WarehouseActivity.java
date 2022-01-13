package com.rfid.pdaapp.activity.puthouse;

import android.os.Bundle;
import android.widget.TextView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ydh on 2022/1/12
 * 收货入库
 */
public class WarehouseActivity extends BaseActivity {

    @BindView(R.id.tv_big_goods)
    TextView tvBigGoods;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_warehouse;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick(R.id.tv_big_goods)
    public void onViewClicked() {
        startActivity(BigGoodsInActivity.class);
    }
}

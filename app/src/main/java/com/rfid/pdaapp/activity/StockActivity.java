package com.rfid.pdaapp.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class StockActivity extends BaseActivity {
    @BindView(R.id.ll_store_house)
    LinearLayout llStoreHouse;
    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.ll_product)
    LinearLayout llProduct;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.iv_box_no)
    ImageView ivBoxNo;
    @BindView(R.id.tv_box_no)
    TextView tvBoxNo;
    @BindView(R.id.ll_box_no)
    LinearLayout llBoxNo;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.tv_clear)
    TextView tvClear;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock;
    }

    @Override
    protected void init() {

    }


    @OnClick({R.id.ll_store_house, R.id.ll_product, R.id.ll_location, R.id.ll_box_no, R.id.tv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_store_house:
                break;
            case R.id.ll_product:
                break;
            case R.id.ll_location:
                break;
            case R.id.ll_box_no:
                break;
            case R.id.tv_clear:
                etNumber.setText("");
                break;
        }
    }
}

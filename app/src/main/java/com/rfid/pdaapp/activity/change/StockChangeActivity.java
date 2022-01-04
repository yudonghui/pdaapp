package com.rfid.pdaapp.activity.change;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 库位移库
 */
public class StockChangeActivity extends BaseActivity {

    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.ll_product)
    LinearLayout llProduct;
    @BindView(R.id.iv_box)
    ImageView ivBox;
    @BindView(R.id.tv_box)
    TextView tvBox;
    @BindView(R.id.ll_box)
    LinearLayout llBox;
    @BindView(R.id.tv_out_library)
    TextView tvOutLibrary;
    @BindView(R.id.tv_out_library_scan)
    TextView tvOutLibraryScan;
    @BindView(R.id.tv_out_box)
    TextView tvOutBox;
    @BindView(R.id.tv_out_box_scan)
    TextView tvOutBoxScan;
    @BindView(R.id.tv_in_library)
    TextView tvInLibrary;
    @BindView(R.id.tv_in_library_scan)
    TextView tvInLibraryScan;
    @BindView(R.id.tv_in_box)
    TextView tvInBox;
    @BindView(R.id.tv_in_box_scan)
    TextView tvInBoxScan;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_change;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_product, R.id.ll_box, R.id.tv_out_library_scan, R.id.tv_out_box_scan, R.id.tv_in_library_scan, R.id.tv_in_box_scan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_product:
                break;
            case R.id.ll_box:
                break;
            case R.id.tv_out_library_scan:
                break;
            case R.id.tv_out_box_scan:
                break;
            case R.id.tv_in_library_scan:
                break;
            case R.id.tv_in_box_scan:
                break;
        }
    }
}
package com.rfid.pdaapp.activity.upper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.HWScanActivity;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.dialogs.DetailDialog;
import com.rfid.pdaapp.utils.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UpperDetailActivity extends BaseActivity {

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
    @BindView(R.id.tv_in_code)
    TextView tvInCode;
    @BindView(R.id.tv_stock_code)
    TextView tvStockCode;
    @BindView(R.id.tv_scan)
    TextView tvScan;
    @BindView(R.id.ll_product_title)
    LinearLayout llProductTitle;
    @BindView(R.id.ll_box_title)
    LinearLayout llBoxTitle;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_num_title)
    TextView tvNumTitle;
    @BindView(R.id.tv_num)
    TextView tvNum;
    private int operationType = 1;//1单品  2箱子
    private boolean isAll = false;//是否全选
    private List<Map<String, Object>> mDataList = new ArrayList<>();
    private String tockCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upper_detail;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        tockCode = getIntent().getStringExtra("tockCode");
        initAdapter();
    }

    private void initAdapter() {

    }


    @OnClick({R.id.ll_product, R.id.ll_box, R.id.tv_stock_code, R.id.tv_detail, R.id.tv_scan, R.id.tv_clear, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_product://单品
                operationType = 1;
                operationType();
                break;
            case R.id.ll_box://箱子
                operationType = 2;
                operationType();
                break;
            case R.id.tv_detail://详情
                new DetailDialog(mContext);
                break;
            case R.id.tv_stock_code:
                break;
            case R.id.tv_scan:
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE0);
                break;
            case R.id.tv_clear:
                break;
            case R.id.tv_confirm:
                break;
        }
    }

    private void operationType() {
        isAll = false;
        if (operationType == 1) {
            ivProduct.setImageResource(R.mipmap.select_icon);
            ivBox.setImageResource(R.mipmap.unselect_icon);
            llBoxTitle.setVisibility(View.GONE);
            llProductTitle.setVisibility(View.VISIBLE);
            tvNumTitle.setText("产品总数: ");
        } else {
            ivProduct.setImageResource(R.mipmap.unselect_icon);
            ivBox.setImageResource(R.mipmap.select_icon);
            llBoxTitle.setVisibility(View.VISIBLE);
            llProductTitle.setVisibility(View.GONE);
            tvNumTitle.setText("箱数: ");
        }
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
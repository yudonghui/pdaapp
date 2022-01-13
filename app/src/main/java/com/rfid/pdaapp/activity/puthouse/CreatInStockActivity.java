package com.rfid.pdaapp.activity.puthouse;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreatInStockActivity extends BaseActivity {

    @BindView(R.id.tv_bill_num)
    EditText tvBillNum;
    @BindView(R.id.tv_cust_name)
    EditText tvCustName;
    @BindView(R.id.tv_remark)
    EditText tvRemark;
    @BindView(R.id.iv_stock_out)
    ImageView ivStockOut;
    @BindView(R.id.tv_stock_out)
    TextView tvStockOut;
    @BindView(R.id.ll_stock_out)
    LinearLayout llStockOut;
    @BindView(R.id.iv_school_out)
    ImageView ivSchoolOut;
    @BindView(R.id.tv_school_out)
    TextView tvSchoolOut;
    @BindView(R.id.ll_school_out)
    LinearLayout llSchoolOut;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_creat_in_stock;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.ll_stock_out, R.id.ll_school_out, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_stock_out:
                break;
            case R.id.ll_school_out:
                break;
            case R.id.tv_submit:
                break;
        }
    }
}

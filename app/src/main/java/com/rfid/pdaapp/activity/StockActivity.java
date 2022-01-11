package com.rfid.pdaapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.utils.CommonUtil;
import com.rfid.pdaapp.utils.Strings;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 库存查询
 */

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
    @BindView(R.id.tv_store_house)
    TextView tvStoreHouse;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    private int type;//0 产品，1库位，3箱号
    private String fStockId;//仓库id
    private String fNumber;//仓库编码
    private String fName;//仓库名称

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


    @OnClick({R.id.ll_store_house, R.id.ll_product, R.id.ll_location, R.id.ll_box_no, R.id.tv_clear, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_store_house:
                startActivityForResult(StockIndexActivity.class, Constant.REQUEST_CODE0);
                break;
            case R.id.ll_product:
                type = 0;
                selectType();
                break;
            case R.id.ll_location:
                type = 1;
                selectType();
                break;
            case R.id.ll_box_no:
                type = 2;
                selectType();
                break;
            case R.id.tv_clear:
                etNumber.setText("");
                break;
            case R.id.tv_search:
                String number = etNumber.getText().toString();
                if (TextUtils.isEmpty(number)) {
                    CommonUtil.showToast("请输入查询编号");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("code", number);
                bundle.putInt("type", type);
                bundle.putString("FStockId_FNumber", fNumber);
                startActivity(StockLocationActivity.class, bundle);
                break;
        }
    }

    private void selectType() {
        if (type == 0) {
            ivProduct.setImageResource(R.mipmap.select_icon);
            ivLocation.setImageResource(R.mipmap.unselect_icon);
            ivBoxNo.setImageResource(R.mipmap.unselect_icon);
        } else if (type == 1) {
            ivProduct.setImageResource(R.mipmap.unselect_icon);
            ivLocation.setImageResource(R.mipmap.select_icon);
            ivBoxNo.setImageResource(R.mipmap.unselect_icon);
        } else {
            ivProduct.setImageResource(R.mipmap.unselect_icon);
            ivLocation.setImageResource(R.mipmap.unselect_icon);
            ivBoxNo.setImageResource(R.mipmap.select_icon);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE0 && resultCode == Constant.RESULT_CODE0) {
            fStockId = data.getStringExtra("FStockId");
            fNumber = data.getStringExtra("FNumber");
            fName = data.getStringExtra("FName");
            tvStoreHouse.setText(Strings.getString(fName));
        }
    }

}

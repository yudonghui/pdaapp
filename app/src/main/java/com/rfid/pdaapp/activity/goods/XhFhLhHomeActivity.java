package com.rfid.pdaapp.activity.goods;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.utils.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class XhFhLhHomeActivity extends BaseActivity {

    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.tv_stock)
    TextView tvStock;
    @BindView(R.id.tv_self)
    TextView tvSelf;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xh_fh_lh_home;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_product, R.id.tv_stock, R.id.tv_self})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_product:
                Bundle bundle = new Bundle();
                startActivity(XhLhActivity.class, bundle);
                break;
            case R.id.tv_stock:
                CommonUtil.showToast("开发中......");
                break;
            case R.id.tv_self:
                CommonUtil.showToast("开发中......");
                break;
        }
    }


}
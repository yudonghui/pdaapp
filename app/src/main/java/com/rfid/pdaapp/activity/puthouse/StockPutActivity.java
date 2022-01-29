package com.rfid.pdaapp.activity.puthouse;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.HWScanActivity;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.ResponeEntity;
import com.rfid.pdaapp.common.SeqHelper;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.common.network.HttpClient;
import com.rfid.pdaapp.dialogs.HisDialog;
import com.rfid.pdaapp.utils.CommonUtil;
import com.rfid.pdaapp.utils.Strings;
import com.rfid.pdaapp.views.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ydh on 2022/1/29
 * 库位上架
 */
public class StockPutActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    @BindView(R.id.tv_library)
    TextView tvLibrary;
    @BindView(R.id.tv_library_scan)
    TextView tvLibraryScan;
    @BindView(R.id.tv_box)
    TextView tvBox;
    @BindView(R.id.tv_box_scan)
    TextView tvBoxScan;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_put;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_library_scan, R.id.tv_box_scan, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_library_scan:
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE0);
                break;
            case R.id.tv_box_scan:
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE1);
                break;
            case R.id.tv_confirm:
                confirm();
                break;
        }
    }

    private void confirm() {
        String F_HFL_CW = tvLibrary.getText().toString();//仓位,扫码获得
        String F_HFL_XH = tvBox.getText().toString();//箱号,扫码获得
        String FBillNo = SeqHelper.genNumber("SHSJ");// 单据编号，在pda里生成，格式：SHSJ-{yyyyMMddHHmmss}
        if (TextUtils.isEmpty(F_HFL_CW)) {
            CommonUtil.showToast("请扫描库位");
            return;
        }
        if (TextUtils.isEmpty(F_HFL_XH)) {
            CommonUtil.showToast("请扫描箱号");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("FormId", "BRE_SHSJCJD");
        HashMap<String, Object> data = new HashMap<>();
        data.put("NeedUpDateFields", new ArrayList<Object>());
        data.put("NeedReturnFields", new ArrayList<Object>());
        data.put("IsDeleteEntry", "true");
        data.put("SubSystemId", "");
        data.put("IsVerifyBaseDataField", "false");
        data.put("IsEntryBatchFill", "true");
        data.put("ValidateFlag", "true");
        data.put("NumberSearch", "true");
        data.put("IsAutoAdjustField", "false");
        data.put("InterationFlags", "");
        data.put("IgnoreInterationFlag", "");
        HashMap<String, Object> model = new HashMap<>();
        model.put("FBillNo", FBillNo);
        model.put("F_HFL_CW", F_HFL_CW);
        model.put("F_HFL_XH", F_HFL_XH);
        data.put("Model", model);
        map.put("data", data);
        showLoadingDialog();
        Call<ResponeEntity> call = HttpClient.getHttpApi().savePutStockBox(HttpClient.getRequestBody(map));
        mNetWorkList.add(call);
        call.enqueue(new Callback<ResponeEntity>() {
            @Override
            public void onResponse(Call<ResponeEntity> call, Response<ResponeEntity> response) {
                cancelLoadingDialog();
                if (response != null && response.isSuccessful() && response.body() != null && response.body().getResult() != null) {
                    ResponeEntity.ResultBean result = response.body().getResult();
                    boolean isSuccess = result.getResponseStatus().isIsSuccess();
                    if (isSuccess) {
                        CommonUtil.showToast("保存成功！");
                        finish();
                    } else {
                        new HisDialog.Builder().title("提示")
                                .message(Strings.getErrorMessage(result.getResponseStatus().getErrors()))
                                .rightBtn("知道了")
                                .build(mContext, 1).show();
                    }
                } else {
                    CommonUtil.showToast("保存失败");
                }
            }

            @Override
            public void onFailure(Call<ResponeEntity> call, Throwable t) {
                cancelLoadingDialog();
                CommonUtil.showToast("保存失败");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE0 && resultCode == Constant.RESULT_CODE0) {//库位
            String scanResult = data.getStringExtra("scanResult");
            tvLibrary.setText(Strings.getString(scanResult));
        } else if (requestCode == Constant.REQUEST_CODE1 && resultCode == Constant.RESULT_CODE0) {//箱号
            String scanResult = data.getStringExtra("scanResult");
            tvBox.setText(Strings.getString(scanResult));
        }
    }
}
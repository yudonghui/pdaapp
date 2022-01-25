package com.rfid.pdaapp.dialogs;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rfid.pdaapp.R;

public class DetailDialog {

    private final BaseDialog mDialog;
    private final View view;
    private LinearLayout llInCode;//入库单号
    private TextView tvInCode;
    private LinearLayout llCreatTime;//创建日期
    private TextView tvCreatTime;
    private LinearLayout llUpperStock;//上架仓库
    private TextView tvUpperStock;
    private LinearLayout llSupplier;//供应商
    private TextView tvSupplier;
    private LinearLayout llSchool;//学校
    private TextView tvSchool;
    private LinearLayout llRemark;//备注
    private TextView tvRemark;

    public DetailDialog(Context mContext) {
        mDialog = new BaseDialog(mContext, R.style.HintDialogInput);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(mContext, R.layout.dialog_detail, null);
        llInCode = view.findViewById(R.id.ll_in_code);
        tvInCode = view.findViewById(R.id.tv_in_code);
        llCreatTime = view.findViewById(R.id.ll_creat_time);
        tvCreatTime = view.findViewById(R.id.tv_creat_time);
        llUpperStock = view.findViewById(R.id.ll_upper_stock);
        tvUpperStock = view.findViewById(R.id.tv_upper_stock);
        llSupplier = view.findViewById(R.id.ll_supplier);
        tvSupplier = view.findViewById(R.id.tv_supplier);
        llSchool = view.findViewById(R.id.ll_school);
        tvSchool = view.findViewById(R.id.tv_school);
        llRemark = view.findViewById(R.id.ll_remark);
        tvRemark = view.findViewById(R.id.tv_remark);
        mDialog.setContentView(view);
        mDialog.show();
    }
}

package com.rfid.pdaapp.activity.change;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.HWScanActivity;
import com.rfid.pdaapp.callback.HisDialogInterface;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.dialogs.HisDialog;
import com.rfid.pdaapp.utils.Strings;
import com.rfid.pdaapp.views.TitleBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ydh on 2022/1/1
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
    @BindView(R.id.iv_data)
    ImageView ivData;
    @BindView(R.id.tv_hint1)
    TextView tvHint1;
    @BindView(R.id.tv_hint2)
    TextView tvHint2;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.ll_product_title)
    LinearLayout llProductTitle;
    @BindView(R.id.ll_box_title)
    LinearLayout llBoxTitle;
    @BindView(R.id.ll_out_box)
    LinearLayout llOutBox;
    @BindView(R.id.tv_box_num)
    TextView tvBoxNum;
    @BindView(R.id.tv_product_num)
    TextView tvProductNum;
    @BindView(R.id.tv_box_title)
    TextView tvBoxTitle;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    private int operationType = 1;//1单品  2箱子
    private boolean isAll = false;//是否全选
    private List<Map<String, Object>> mDataList = new ArrayList<>();
    private CommonAdapter<Map<String, Object>> mCommonAdapter;
    private String[] mFieldKeys = {"code", "num"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_change;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initAdapter();
        initListener();
    }


    @OnClick({R.id.ll_product, R.id.ll_box, R.id.tv_out_library_scan, R.id.tv_out_box_scan,
            R.id.tv_in_library_scan, R.id.tv_in_box_scan, R.id.iv_data, R.id.tv_clear, R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_product://单品
                operationType = 1;
                operationType();
                break;
            case R.id.ll_box://箱子
                operationType = 2;
                operationType();
                break;
            case R.id.tv_out_library_scan:
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE0);
                break;
            case R.id.tv_out_box_scan:
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE1);
                break;
            case R.id.tv_in_library_scan:
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE2);
                break;
            case R.id.tv_in_box_scan:
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE3);
                break;
            case R.id.iv_data:
                getData();
                break;
            case R.id.tv_clear:
                new HisDialog.Builder().title("提示")
                        .message("是否确定清除")
                        .cancel("取消")
                        .confirm("确认", new HisDialogInterface() {
                            @Override
                            public void callBack(View view) {

                            }
                        })
                        .build(mContext);
                break;
            case R.id.tv_confirm:
                break;
        }
    }


    private void initListener() {
        tvTitle.setListener(new TitleBar.TextListener() {//全选
            @Override
            public void onClick(TextView textView) {
                isAll = !isAll;
                for (int i = 0; i < mDataList.size(); i++) {
                    mDataList.get(i).put("check", isAll);
                }
                mCommonAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        String strByArray = Strings.getStrByArray(mFieldKeys);
        if (operationType == 1) {
            for (int i = 0; i < 20; i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("code", "HD200010A165");
                map.put("num", "5");
                map.put("check", false);
                mDataList.add(map);
            }
        } else {
            for (int i = 0; i < 20; i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("code", "CKXH202003310046");
                map.put("num", "1箱[150件]");
                map.put("check", false);
                mDataList.add(map);
            }
        }
        mCommonAdapter.notifyDataSetChanged();
    }
    private void initAdapter() {
        mCommonAdapter = new CommonAdapter<Map<String, Object>>(mContext, R.layout.item_stock_change, mDataList) {

            @Override
            protected void convert(ViewHolder holder, Map<String, Object> stringObjectMap, int position) {
                if (operationType == 1) {
                    holder.setText(R.id.tv_code, Strings.getStringL(stringObjectMap.get("code")));
                    holder.setText(R.id.tv_num, Strings.getStringL(stringObjectMap.get("num")));
                    boolean check = (boolean) stringObjectMap.get("check");
                    holder.setImageResource(R.id.iv_check, check ? R.mipmap.select_icon : R.mipmap.unselect_icon);

                } else {
                    holder.setText(R.id.tv_code, Strings.getStringL(stringObjectMap.get("code")));
                    holder.setText(R.id.tv_num, Strings.getStringL(stringObjectMap.get("num")));
                    boolean check = (boolean) stringObjectMap.get("check");
                    holder.setImageResource(R.id.iv_check, check ? R.mipmap.select_icon : R.mipmap.unselect_icon);
                }
                holder.setOnClickListener(R.id.iv_check, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stringObjectMap.put("check", !((boolean) stringObjectMap.get("check")));
                        holder.setImageResource(R.id.iv_check, (boolean) stringObjectMap.get("check") ? R.mipmap.select_icon : R.mipmap.unselect_icon);
                    }
                });
            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.HORIZONTAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mCommonAdapter);
    }

    private boolean isSelect = false;

    private void getData() {
        if (isSelect) {
            ivData.setImageResource(R.mipmap.unselect_more_icon);
            isSelect = false;
            mDataList.clear();
            mCommonAdapter.notifyDataSetChanged();
        } else {
            ivData.setImageResource(R.mipmap.select_more_icon);
            isSelect = true;
            initData();
        }
    }

    private void operationType() {
        isAll = false;
        ivData.setImageResource(R.mipmap.unselect_more_icon);
        isSelect = false;
        mDataList.clear();
        mCommonAdapter.notifyDataSetChanged();
        if (operationType == 1) {
            ivProduct.setImageResource(R.mipmap.select_icon);
            ivBox.setImageResource(R.mipmap.unselect_icon);
            tvHint1.setText("列出产品");
            tvHint2.setVisibility(View.VISIBLE);
            llBoxTitle.setVisibility(View.GONE);
            llProductTitle.setVisibility(View.VISIBLE);
            llOutBox.setVisibility(View.VISIBLE);
            tvBoxTitle.setVisibility(View.GONE);
            tvBoxNum.setVisibility(View.GONE);
        } else {
            ivProduct.setImageResource(R.mipmap.unselect_icon);
            ivBox.setImageResource(R.mipmap.select_icon);
            tvHint1.setText("列出对应库位中的箱子");
            tvHint2.setVisibility(View.GONE);
            llBoxTitle.setVisibility(View.VISIBLE);
            llProductTitle.setVisibility(View.GONE);
            llOutBox.setVisibility(View.GONE);
            tvBoxTitle.setVisibility(View.VISIBLE);
            tvBoxNum.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE0 && resultCode == Constant.RESULT_CODE0) {//移出库位
            String scanResult = data.getStringExtra("scanResult");
            tvOutLibrary.setText(Strings.getString(scanResult));
        } else if (requestCode == Constant.REQUEST_CODE1 && resultCode == Constant.RESULT_CODE0) {//移出箱子
            String scanResult = data.getStringExtra("scanResult");
            tvOutBox.setText(Strings.getString(scanResult));
        } else if (requestCode == Constant.REQUEST_CODE2 && resultCode == Constant.RESULT_CODE0) {//移入库位
            String scanResult = data.getStringExtra("scanResult");
            tvInLibrary.setText(Strings.getString(scanResult));
        } else if (requestCode == Constant.REQUEST_CODE3 && resultCode == Constant.RESULT_CODE0) {//移入箱子
            String scanResult = data.getStringExtra("scanResult");
            tvInBox.setText(Strings.getString(scanResult));
        }
    }

}
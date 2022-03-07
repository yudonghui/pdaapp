package com.rfid.pdaapp.activity.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.HWScanActivity;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.dialogs.EditeDialog;
import com.rfid.pdaapp.entitys.StockGoodsEntity;
import com.rfid.pdaapp.utils.Strings;
import com.rfid.pdaapp.views.TitleBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CbLhActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    @BindView(R.id.tv_fbillno)
    TextView tvFbillno;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.tv_require)
    TextView tvRequire;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_total_num)
    TextView tvTotalNum;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private List<StockGoodsEntity> mDataList = new ArrayList<>();
    private CommonAdapter<StockGoodsEntity> mAdapter;
    private String FBillNo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cb_lh;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        FBillNo = getIntent().getStringExtra("FBillNo");
        tvFbillno.setText(Strings.getString(FBillNo));
        initListener();
        initAdapter();
        mDataList.add(new StockGoodsEntity(""));
    }

    @OnClick({R.id.tv_add, R.id.tv_clear, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                mDataList.add(new StockGoodsEntity());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_clear:
                mDataList.clear();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_confirm:
                break;
        }
    }

    private void initListener() {

    }

    private int clickPosition = 0;

    private void initAdapter() {
        mAdapter = new CommonAdapter<StockGoodsEntity>(mContext, R.layout.item_cb_lh, mDataList) {

            @Override
            protected void convert(ViewHolder viewHolder, StockGoodsEntity stockGoodsEntity, int position) {
                viewHolder.setText(R.id.tv_stock, Strings.getString(stockGoodsEntity.getStock()));
                viewHolder.setText(R.id.tv_goods, Strings.getString(stockGoodsEntity.getGoods()));
                TextView mTvNum = viewHolder.getView(R.id.tv_num);
                mTvNum.setText(stockGoodsEntity.getNum() + "");
                viewHolder.setOnClickListener(R.id.tv_sub_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = stockGoodsEntity.getNum();
                        if (num > 0) {
                            num--;
                            stockGoodsEntity.setNum(num);
                            mTvNum.setText(num + "");
                        }
                    }
                });
                viewHolder.setOnClickListener(R.id.tv_add_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = stockGoodsEntity.getNum();
                        num++;
                        stockGoodsEntity.setNum(num);
                        mTvNum.setText(num + "");
                    }
                });
                viewHolder.setOnClickListener(R.id.tv_stock, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickPosition = position;
                        startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE0);
                    }
                });
                viewHolder.setOnClickListener(R.id.tv_goods, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickPosition = position;
                        startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE1);
                    }
                });
                viewHolder.setOnClickListener(R.id.tv_num, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new EditeDialog(mContext, stockGoodsEntity.getNum() + "", 1, "请输入", new EditeDialog.EditInterface() {
                            @Override
                            public void onClick(String s) {
                                viewHolder.setText(R.id.tv_num, Strings.getString(s));
                                stockGoodsEntity.setNum(Strings.getInt(s));
                            }
                        });
                    }
                });
                viewHolder.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDataList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.HORIZONTAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE0 && resultCode == Constant.RESULT_CODE0) {//扫描的库位
            String scanResult = data.getStringExtra("scanResult");
            setStock(scanResult);
        } else if (requestCode == Constant.REQUEST_CODE1 && resultCode == Constant.RESULT_CODE0) {//扫描的商品
            String scanResult = data.getStringExtra("scanResult");
            setGoods(scanResult);
        }
    }

    private void setStock(String result) {
        mDataList.get(clickPosition).setStock(result);
        mDataList.get(clickPosition).setGoods("");
        mDataList.get(clickPosition).setNum(0);
        mAdapter.notifyDataSetChanged();
    }

    private void setGoods(String result) {
        StockGoodsEntity stockGoodsEntity = mDataList.get(clickPosition);
        stockGoodsEntity.setGoods(result);
        stockGoodsEntity.setNum(stockGoodsEntity.getNum() + 1);
        mAdapter.notifyDataSetChanged();
    }


}
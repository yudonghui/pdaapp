package com.rfid.pdaapp.activity;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.entitys.StockEntity;
import com.rfid.pdaapp.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class StockFormActivity extends BaseActivity {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_box_num)
    TextView tvBoxNum;
    @BindView(R.id.tv_product_num)
    TextView tvProductNum;
    @BindView(R.id.rv_stock)
    RecyclerView rvStock;
    @BindView(R.id.tv_continue)
    TextView tvContinue;
    @BindView(R.id.tv_back_main)
    TextView tvBackMain;
    ArrayList<StockEntity> mStockList = new ArrayList<>();
    private CommonAdapter<StockEntity> mStockAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_form;
    }

    @Override
    protected void init() {
        initAdapter();
        initData();
    }


    @OnClick({R.id.tv_continue, R.id.tv_back_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_continue:
                finish();
                break;
            case R.id.tv_back_main:
                startActivity(MainActivity.class);
                break;
        }
    }

    private void initAdapter() {

        mStockAdapter = new CommonAdapter<StockEntity>(mContext, R.layout.item_stock, mStockList) {

            @Override
            protected void convert(ViewHolder holder, StockEntity stockEntity, int position) {
                holder.setText(R.id.tv_size, Strings.getString(stockEntity.getSize()));
                holder.setText(R.id.tv_use_stock, Strings.getString(stockEntity.getUser()) + "/" + Strings.getString(stockEntity.getRealStock()));
                holder.setText(R.id.tv_box_no, Strings.getString(stockEntity.getBoxNum()));
                holder.setText(R.id.tv_location, Strings.getString(stockEntity.getLocation()));
            }
        };
        rvStock.setLayoutManager(new LinearLayoutManager(mContext));
        rvStock.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvStock.setAdapter(mStockAdapter);
    }


    private void initData() {
        mStockList.clear();
        mStockList.add(new StockEntity("150", "2", "24", "5", "4_SA44Z02C04"));
        mStockList.add(new StockEntity("151", "3", "46", "434", "5_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "4", "45", "12", "6_SA44Z02C04"));
        mStockList.add(new StockEntity("130", "5", "33", "22", "3_SA44Z02C04"));
        mStockList.add(new StockEntity("110", "6", "12", "23", "1_SA44Z02C04"));
        mStockList.add(new StockEntity("0", "12", "50", "6", "2_SA44Z02C04"));
        mStockList.add(new StockEntity("15", "44", "34", "23", "12_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockList.add(new StockEntity("10", "2", "50", "23", "34_SA44Z02C04"));
        mStockAdapter.notifyDataSetChanged();
    }
}

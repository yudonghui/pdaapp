package com.rfid.pdaapp.activity.inventory;

import android.os.Bundle;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.entitys.InventoryEntity;
import com.rfid.pdaapp.entitys.InventoryFormEntity;
import com.rfid.pdaapp.views.TitleBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by ydh on 2022/1/24
 */
public class InventoryFormActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    private CommonAdapter<InventoryEntity> mCommonAdapter;
    private List<InventoryEntity> mDataList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inventory_form;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initAdapter();
        initListener();
        initData();
    }

    private void initListener() {
        tvTitle.setListener(new TitleBar.TextListener() {
            @Override
            public void onClick(TextView textView) {

            }
        });
    }

    private void initAdapter() {

        mCommonAdapter = new CommonAdapter<InventoryEntity>(mContext, R.layout.item_inventory_form, mDataList) {

            @Override
            protected void convert(ViewHolder holder, InventoryEntity inventoryEntity, int position) {
                RecyclerView mRvChild = holder.getView(R.id.rv_child);
                List<InventoryFormEntity> childList = inventoryEntity.getList() == null ? new ArrayList<>() : inventoryEntity.getList();
                CommonAdapter<InventoryFormEntity> mChildAdapter = new CommonAdapter<InventoryFormEntity>(mContext, R.layout.item_inventory_form_child, childList) {

                    @Override
                    protected void convert(ViewHolder holder, InventoryFormEntity inventoryFormEntity, int position) {

                    }
                };
                mRvChild.setLayoutManager(new LinearLayoutManager(mContext));
                mRvChild.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
                mRvChild.setAdapter(mChildAdapter);
            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(mContext, R.color.transparent)));
        rvData.setAdapter(mCommonAdapter);
    }

    private void initData() {
        mDataList.clear();
        for (int i = 0; i < 10; i++) {
            InventoryEntity inventoryEntity = new InventoryEntity();
            ArrayList<InventoryFormEntity> list = new ArrayList<>();
            int i1 = new Random().nextInt(8);
            for (int j = 0; j < i1; j++) {
                list.add(new InventoryFormEntity());
            }
            inventoryEntity.setList(list);
            mDataList.add(inventoryEntity);
        }
        mCommonAdapter.notifyDataSetChanged();
    }

}
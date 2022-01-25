package com.rfid.pdaapp.activity.inventory;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.SpaceItemDecoration;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.entitys.HomeEntity;
import com.rfid.pdaapp.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by ydh on 2022/1/24
 */
public class InventoryActivity extends BaseActivity {

    @BindView(R.id.rv_inventory)
    RecyclerView rvInventory;
    private CommonAdapter<HomeEntity> mHomeAdapter;
    private ArrayList<HomeEntity> mHomeList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inventory;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        rvInventory.setLayoutManager(gridLayoutManager);
        rvInventory.addItemDecoration(new SpaceItemDecoration(10, 10));
        mHomeAdapter = new CommonAdapter<HomeEntity>(mContext, R.layout.item_home, mHomeList) {

            @Override
            protected void convert(ViewHolder holder, HomeEntity homeEntity, int position) {
                TextView mTvContent = holder.getView(R.id.tv_content);
                mTvContent.setText(Strings.getString(homeEntity.getContent()));
                mTvContent.setBackgroundResource(homeEntity.getResId());
                mTvContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (homeEntity.getType()) {
                            case 0://按产品
                                startActivity(InventoryProductActivity.class);
                                break;
                            case 1://按库位
                                startActivity(InventoryProductActivity.class);
                                break;
                        }

                    }
                });
            }
        };
        rvInventory.setAdapter(mHomeAdapter);
        initData();
    }

    private void initData() {
        mHomeList.add(new HomeEntity("按产品", 0, R.drawable.shape_theme_10));
        mHomeList.add(new HomeEntity("按库位", 1, R.drawable.shape_green_10));
        mHomeAdapter.notifyDataSetChanged();
    }

}
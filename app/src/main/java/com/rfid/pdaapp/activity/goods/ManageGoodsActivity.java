package com.rfid.pdaapp.activity.goods;

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
 * Created by ydh on 2022/3/6
 * 波次理货
 */
public class ManageGoodsActivity extends BaseActivity {

    @BindView(R.id.rv_manage_goods)
    RecyclerView rvManageGoods;
    private CommonAdapter<HomeEntity> mHomeAdapter;
    private ArrayList<HomeEntity> mHomeList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_goods;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initAdapter();
        initData();
    }

    private void initAdapter() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        rvManageGoods.setLayoutManager(gridLayoutManager);
        rvManageGoods.addItemDecoration(new SpaceItemDecoration(10, 10));
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
                            case 1://现货发货理货
                                startActivity(XhFhLhActivity.class);
                                break;

                        }

                    }
                });
            }
        };
        rvManageGoods.setAdapter(mHomeAdapter);
    }

    private void initData() {
        mHomeList.add(new HomeEntity("现货发货理货", 1, R.drawable.shape_theme_10));
        mHomeAdapter.notifyDataSetChanged();
    }
}
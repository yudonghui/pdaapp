package com.rfid.pdaapp.activity;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.SpaceItemDecoration;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.entitys.HomeEntity;
import com.rfid.pdaapp.utils.Strings;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.refrashlayout)
    SmartRefreshLayout refrashlayout;
    private CommonAdapter<HomeEntity> mHomeAdapter;
    private ArrayList<HomeEntity> mHomeList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
        rvHome.setLayoutManager(gridLayoutManager);
        rvHome.addItemDecoration(new SpaceItemDecoration(10, 10));
        mHomeAdapter = new CommonAdapter<HomeEntity>(mContext, R.layout.item_home, mHomeList) {

            @Override
            protected void convert(ViewHolder holder, HomeEntity homeEntity, int position) {
                holder.setText(R.id.tv_content, Strings.getString(homeEntity.getContent()));
                holder.setOnClickListener(R.id.tv_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(StockFormActivity.class);
                    }
                });
            }
        };
        rvHome.setAdapter(mHomeAdapter);
        initData();
    }

    private void initData() {
        mHomeList.add(new HomeEntity("库存查询"));
        mHomeList.add(new HomeEntity("叫号管理"));
        mHomeList.add(new HomeEntity("收费单"));
        mHomeList.add(new HomeEntity("建档"));
        mHomeList.add(new HomeEntity("医生查询"));
        mHomeAdapter.notifyDataSetChanged();
    }

}

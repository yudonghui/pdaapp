package com.rfid.pdaapp.activity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.entitys.HomeEntity;
import com.rfid.pdaapp.utils.DisplayUtil;
import com.rfid.pdaapp.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    private CommonAdapter<HomeEntity> mHomeAdapter;
    private ArrayList<HomeEntity> mHomeList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        rvHome.setLayoutManager(gridLayoutManager);
        rvHome.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.HORIZONTAL, DisplayUtil.dp2px(10), ContextCompat.getColor(mContext, R.color.transparent)));
       // rvHome.addItemDecoration(new MyItemDecoration());
        mHomeAdapter = new CommonAdapter<HomeEntity>(mContext, R.layout.item_home, mHomeList) {

            @Override
            protected void convert(ViewHolder holder, HomeEntity homeEntity, int position) {
                holder.setText(R.id.tv_content, Strings.getString(homeEntity.getContent()));
            }
        };
        rvHome.setAdapter(mHomeAdapter);
        initData();
    }

    private void initData() {
        mHomeList.add(new HomeEntity("预约管理"));
        mHomeList.add(new HomeEntity("叫号管理"));
        mHomeList.add(new HomeEntity("收费单"));
        mHomeList.add(new HomeEntity("建档"));
        mHomeList.add(new HomeEntity("医生查询"));
        mHomeAdapter.notifyDataSetChanged();
    }

}

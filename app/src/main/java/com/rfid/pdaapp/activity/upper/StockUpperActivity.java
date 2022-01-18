package com.rfid.pdaapp.activity.upper;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.SpaceItemDecoration;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.entitys.HomeEntity;
import com.rfid.pdaapp.utils.Strings;
import com.rfid.pdaapp.views.TitleBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;

public class StockUpperActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    private CommonAdapter<HomeEntity> mHomeAdapter;
    private ArrayList<HomeEntity> mHomeList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stock_upper;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        rvHome.setLayoutManager(gridLayoutManager);
        rvHome.addItemDecoration(new SpaceItemDecoration(10, 10));
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
                            case Constant.UPPER_PTSJ://普通上架
                            case Constant.UPPER_JKSJ://减库上架
                            case Constant.UPPER_JGSJ://加工上架
                            case Constant.UPPER_SDCSHSJ://锁定仓收货上架
                                Bundle bundle = new Bundle();
                                bundle.putInt("type", homeEntity.getType());
                                startActivity(UpperFormActivity.class, bundle);
                                break;
                            case Constant.UPPER_DHTHSJ://大货退货上架
                                startActivity(UpperBigActivity.class);
                                break;
                        }

                    }
                });
            }
        };
        rvHome.setAdapter(mHomeAdapter);
        initData();
    }

    private void initData() {
        mHomeList.add(new HomeEntity("普通上架", Constant.UPPER_PTSJ, R.drawable.shape_theme_10));
        mHomeList.add(new HomeEntity("减库上架", Constant.UPPER_JKSJ, R.drawable.shape_green_10));
        mHomeList.add(new HomeEntity("加工上架", Constant.UPPER_JGSJ, R.drawable.shape_green_10));
        mHomeList.add(new HomeEntity("大货退货上架", Constant.UPPER_DHTHSJ, R.drawable.shape_theme_10));
        mHomeList.add(new HomeEntity("锁定仓收货上架", Constant.UPPER_SDCSHSJ, R.drawable.shape_theme_10));
        mHomeAdapter.notifyDataSetChanged();
    }
}
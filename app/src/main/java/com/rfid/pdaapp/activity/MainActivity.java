package com.rfid.pdaapp.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.change.StockChangeActivity;
import com.rfid.pdaapp.activity.inventory.InventoryActivity;
import com.rfid.pdaapp.activity.puthouse.WarehouseActivity;
import com.rfid.pdaapp.activity.upper.StockUpperActivity;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.SPUtils;
import com.rfid.pdaapp.common.SpaceItemDecoration;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.common.network.HttpClient;
import com.rfid.pdaapp.common.updateapp.CustomUpdateParser;
import com.rfid.pdaapp.common.updateapp.CustomUpdatePrompter;
import com.rfid.pdaapp.entitys.HomeEntity;
import com.rfid.pdaapp.utils.Strings;
import com.xuexiang.xupdate.XUpdate;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.tv_quite)
    TextView tvQuite;
    private CommonAdapter<HomeEntity> mHomeAdapter;
    private ArrayList<HomeEntity> mHomeList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        checkUpdate();
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
                            case Constant.HOME_KCCX://库位查询
                                startActivity(StockActivity.class);
                                break;
                            case Constant.HOME_KWYK://库位移库
                                startActivity(StockChangeActivity.class);
                                break;
                            case Constant.HOME_KWTZ://库位调整
                                startActivity(StockAdjustActivity.class);
                                break;
                            case Constant.HOME_SHRK://收货入库
                                startActivity(WarehouseActivity.class);
                                break;
                            case Constant.HOME_KWSJ://库位上架
                                startActivity(StockUpperActivity.class);
                                break;
                            case Constant.HOME_PD://盘点
                                startActivity(InventoryActivity.class);
                                break;
                        }

                    }
                });
            }
        };
        rvHome.setAdapter(mHomeAdapter);
        initData();
    }

    @OnClick({R.id.tv_quite})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_quite:
                quiteLogin();
                break;
        }
    }

    private void quiteLogin() {
        Call<Object> call = HttpClient.getHttpApi().loginQuite(HttpClient.getRequestBody(new HashMap<>()));
        mNetWorkList.add(call);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                SPUtils.clearCache(SPUtils.FILE_USER);
                startActivity(LoginActivity.class);
                finish();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void initData() {
        mHomeList.add(new HomeEntity("库存查询", Constant.HOME_KCCX, R.drawable.shape_theme_10));
        mHomeList.add(new HomeEntity("库存移库", Constant.HOME_KWYK, R.drawable.shape_green_10));
        mHomeList.add(new HomeEntity("库位调整", Constant.HOME_KWTZ, R.drawable.shape_green_10));
        mHomeList.add(new HomeEntity("收货入库", Constant.HOME_SHRK, R.drawable.shape_theme_10));
        mHomeList.add(new HomeEntity("库位上架", Constant.HOME_KWSJ, R.drawable.shape_theme_10));
        mHomeList.add(new HomeEntity("盘点", Constant.HOME_PD, R.drawable.shape_green_10));
        mHomeAdapter.notifyDataSetChanged();
    }

    private void checkUpdate() {
        XUpdate.newBuild(this)
                .updateUrl(Constant.UPDATE_URL)
                .updateParser(new CustomUpdateParser())
                .updatePrompter(new CustomUpdatePrompter(this))
                .update();
    }
}

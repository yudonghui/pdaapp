package com.rfid.pdaapp.activity;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.SPUtils;
import com.rfid.pdaapp.common.SpaceItemDecoration;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.common.network.HttpClient;
import com.rfid.pdaapp.entitys.HomeEntity;
import com.rfid.pdaapp.utils.Strings;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        rvHome.setLayoutManager(gridLayoutManager);
        rvHome.addItemDecoration(new SpaceItemDecoration(10, 10));
        mHomeAdapter = new CommonAdapter<HomeEntity>(mContext, R.layout.item_home, mHomeList) {

            @Override
            protected void convert(ViewHolder holder, HomeEntity homeEntity, int position) {
                holder.setText(R.id.tv_content, Strings.getString(homeEntity.getContent()));
                holder.setOnClickListener(R.id.tv_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(StockActivity.class);
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
        mHomeList.add(new HomeEntity("库存查询"));
        mHomeAdapter.notifyDataSetChanged();
    }

}

package com.rfid.pdaapp.activity.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.HWScanActivity;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.entitys.InventoryEntity;
import com.rfid.pdaapp.entitys.InventoryFormEntity;
import com.rfid.pdaapp.utils.LogUtils;
import com.rfid.pdaapp.views.ErrorView;
import com.rfid.pdaapp.views.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

/**
 * Created by ydh on 2022/3/6
 * 现货理货
 */
public class XhLhActivity extends BaseActivity {

    @BindView(R.id.error_view)
    ErrorView errorView;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    private CommonAdapter<InventoryEntity> mCommonAdapter;
    List<InventoryEntity> mDataList = new ArrayList<>();
    private String FID;
    private String FBillNo;
    private String F_HFL_SCH_FNAME;
    private String F_HFL_SCH_FNUMBER;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xh_lh;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        FID = getIntent().getStringExtra("FID");
        FBillNo = getIntent().getStringExtra("FBillNo");
        F_HFL_SCH_FNAME = getIntent().getStringExtra("F_HFL_SCH.FNAME");
        F_HFL_SCH_FNUMBER = getIntent().getStringExtra("F_HFL_SCH.FNUMBER ");
        initAdapter();
        initListener();
        initData();
    }

    private void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData();
            }
        });
        tvTitle.setListener(new TitleBar.TextListener() {
            @Override
            public void onClick(TextView textView) {
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE0);
            }
        });
    }

    private void initAdapter() {

        mCommonAdapter = new CommonAdapter<InventoryEntity>(mContext, R.layout.item_xh_lh, mDataList) {

            @Override
            protected void convert(ViewHolder holder, InventoryEntity inventoryEntity, int position) {
                RecyclerView mRvChild = holder.getView(R.id.rv_child);
                ImageView mIvOpenClose = holder.getView(R.id.iv_open_close);
                List<InventoryFormEntity> childList = inventoryEntity.getList() == null ? new ArrayList<>() : inventoryEntity.getList();
                CommonAdapter<InventoryFormEntity> mChildAdapter = new CommonAdapter<InventoryFormEntity>(mContext, R.layout.item_inventory_form_child, childList) {

                    @Override
                    protected void convert(ViewHolder childHolder, InventoryFormEntity inventoryFormEntity, int position) {
                        childHolder.setOnClickListener(R.id.ll_child_content, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString("FBillNo", FBillNo);
                                bundle.putString("FID", FID);
                                bundle.putString("F_HFL_SCH_FNAME", F_HFL_SCH_FNAME);
                                bundle.putString("F_HFL_SCH_FNUMBER", F_HFL_SCH_FNUMBER);
                                startActivity(CbLhActivity.class, bundle);
                            }
                        });
                    }
                };
                mRvChild.setLayoutManager(new LinearLayoutManager(mContext));
                mRvChild.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.VERTICAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
                mRvChild.setAdapter(mChildAdapter);
                if (inventoryEntity.isExpand()) {
                    mRvChild.setVisibility(View.VISIBLE);
                    mIvOpenClose.setImageResource(R.mipmap.open_white);
                } else {
                    mRvChild.setVisibility(View.GONE);
                    mIvOpenClose.setImageResource(R.mipmap.close_white);
                }
                holder.setOnClickListener(R.id.ll_open_close, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inventoryEntity.setExpand(!inventoryEntity.isExpand());
                        if (inventoryEntity.isExpand()) {
                            mRvChild.setVisibility(View.VISIBLE);
                            mIvOpenClose.setImageResource(R.mipmap.open_white);
                        } else {
                            mRvChild.setVisibility(View.GONE);
                            mIvOpenClose.setImageResource(R.mipmap.close_white);
                        }
                    }
                });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE0 && resultCode == Constant.RESULT_CODE0) {//移出库位
            String scanResult = data.getStringExtra("scanResult");
            LogUtils.e(scanResult);
        }
    }
}
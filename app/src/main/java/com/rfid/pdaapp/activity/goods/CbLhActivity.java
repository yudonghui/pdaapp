package com.rfid.pdaapp.activity.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.HWScanActivity;
import com.rfid.pdaapp.common.CommonParams;
import com.rfid.pdaapp.common.Constant;
import com.rfid.pdaapp.common.RecyclerViewDivider;
import com.rfid.pdaapp.common.SPUtils;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.common.network.HttpClient;
import com.rfid.pdaapp.dialogs.EditeDialog;
import com.rfid.pdaapp.entitys.StockGoodsEntity;
import com.rfid.pdaapp.utils.CommonUtil;
import com.rfid.pdaapp.utils.DateFormtUtils;
import com.rfid.pdaapp.utils.Strings;
import com.rfid.pdaapp.views.TitleBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CbLhActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleBar tvTitle;
    @BindView(R.id.tv_fbillno)
    TextView tvFbillno;
    @BindView(R.id.tv_product)
    TextView tvProduct;
    @BindView(R.id.tv_require)
    TextView tvRequire;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_total_num)
    TextView tvTotalNum;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    private List<StockGoodsEntity> mDataList = new ArrayList<>();
    private CommonAdapter<StockGoodsEntity> mAdapter;
    private String FBillNo;
    private String FID;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cb_lh;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        FBillNo = getIntent().getStringExtra("FBillNo");
        FID = getIntent().getStringExtra("FID");
        tvFbillno.setText(Strings.getString(FBillNo));
        initListener();
        initAdapter();
        mDataList.add(new StockGoodsEntity(""));
    }

    @OnClick({R.id.tv_add, R.id.tv_clear, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                mDataList.add(new StockGoodsEntity());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_clear:
                mDataList.clear();
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_confirm:
                if (mDataList.size() == 0) {
                    CommonUtil.showToast("请扫描库位/商品");
                    return;
                }
                boolean isData = false;
                for (int i = 0; i < mDataList.size(); i++) {
                    if (ObjectUtils.isNotEmpty(mDataList.get(i).getStock()) && ObjectUtils.isNotEmpty(mDataList.get(i).getGoods())) {
                        isData = true;
                        break;
                    }
                }
                if (!isData) {
                    CommonUtil.showToast("请扫描库位/商品");
                    return;
                }
                for (int i = 0; i < mDataList.size(); i++) {
                    if (ObjectUtils.isNotEmpty(mDataList.get(i).getStock()) && ObjectUtils.isNotEmpty(mDataList.get(i).getGoods())) {
                        confirm(i);
                    }
                }

                break;
        }
    }

    /**
     * 实体主键：FID
     * 单据编号：FBillNo
     * 数据状态：FDocumentStatus
     * 日期：F_HFL_date
     * 学校：F_HFL_XX
     * 产品编码(基础资料)：F_HFL_CPBM
     * 商品编码：F_HFL_SPBM
     * 尺码(基础资料)：F_HFL_CM
     * 备注：F_HFL_BZ
     * 客户(基础资料)：F_HFL_KH
     * 辅助属性：F_HFL_FZSX
     * 学校：FF100001
     * 尺码：FF100004
     * 规格：FF100005
     * 创建人：FCreatorId
     * 创建日期：FCreateDate
     * 修改人：FModifierId
     * 修改日期：FModifyDate
     * 审核人：F_HFL_SHR
     * 审核日期：F_HFL_SHRQ
     * 商品名称：F_HFL_CPMC
     * 产品编码：F_HFL_CPBMDA
     * 尺码：F_HFL_CMDA
     * 销售组织：F_HFL_SALOrgId
     * 结算组织：F_HFL_SetOrg
     * 关联发货单号：F_HFL_GLFHTZDH
     * 业务类型：F_HFL_YWLX
     * 数量：F_HFL_QTY
     * 计量单位：F_HFL_UnitID
     * 单据类型：F_BRE_BillTypeID  (必填项)
     **/
    private void confirm(int position) {
        StockGoodsEntity stockGoodsEntity = mDataList.get(position);

        HashMap<String, Object> map = new HashMap<>();
        map.put("FormId", CommonParams.FormId);
        HashMap<String, Object> data = new HashMap<>();
        data.put("IsDeleteEntry", "true");
        data.put("SubSystemId", "");
        data.put("IsVerifyBaseDataField", "false");
        data.put("IsEntryBatchFill", "true");
        data.put("ValidateFlag", "true");
        data.put("NumberSearch", "true");
        data.put("IsAutoAdjustField", "false");
        data.put("InterationFlags", "");
        data.put("IgnoreInterationFlag", "");
        data.put("NeedUpDateFields", new ArrayList<String>());
        data.put("NeedReturnFields", new ArrayList<String>());
        HashMap<String, Object> model = new HashMap<>();
        model.put("FID", FID);
        model.put("FBILLNO", FBillNo);//LHSJ000008
        model.put("F_HFL_date", "");//2022-03-06 00:00:00
        model.put("F_HFL_XX", "");//惠灵顿国际学校（集团）
        model.put("F_HFL_CPBMDA", "");//产品编码
        model.put("F_HFL_SPBM", "");//商品编码
        model.put("F_HFL_CPMC", "");//商品名称
        model.put("F_HFL_CMDA", "");//尺码
        model.put("F_HFL_BZ", "");//备注
        model.put("F_HFL_QTY", "");//数量

        model.put("FCreateDate", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));//

        HashMap<String, Object> FCreatorId = new HashMap<>();
        FCreatorId.put("FUserID", SPUtils.getCache(SPUtils.FILE_USER, SPUtils.USER_ID));
        model.put("FCreatorId", FCreatorId);

        HashMap<String, Object> F_BRE_BillTypeID = new HashMap<>();
        F_BRE_BillTypeID.put("FNUMBER", "");
        model.put("F_BRE_BillTypeID", F_BRE_BillTypeID);//数量

        data.put("Model", model);

        map.put("data", data);
        Call<ResponseBody> call = HttpClient.getHttpApi().receiveOrFinishTask(HttpClient.getRequestBody(map));
        mNetWorkList.add(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mDataList.remove(position);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void initListener() {

    }

    private int clickPosition = 0;

    private void initAdapter() {
        mAdapter = new CommonAdapter<StockGoodsEntity>(mContext, R.layout.item_cb_lh, mDataList) {

            @Override
            protected void convert(ViewHolder viewHolder, StockGoodsEntity stockGoodsEntity, int position) {
                viewHolder.setText(R.id.tv_stock, Strings.getString(stockGoodsEntity.getStock()));
                viewHolder.setText(R.id.tv_goods, Strings.getString(stockGoodsEntity.getGoods()));
                TextView mTvNum = viewHolder.getView(R.id.tv_num);
                mTvNum.setText(stockGoodsEntity.getNum() + "");
                viewHolder.setOnClickListener(R.id.tv_sub_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = stockGoodsEntity.getNum();
                        if (num > 0) {
                            num--;
                            stockGoodsEntity.setNum(num);
                            mTvNum.setText(num + "");
                        }
                    }
                });
                viewHolder.setOnClickListener(R.id.tv_add_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = stockGoodsEntity.getNum();
                        num++;
                        stockGoodsEntity.setNum(num);
                        mTvNum.setText(num + "");
                    }
                });
                viewHolder.setOnClickListener(R.id.tv_stock, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickPosition = position;
                        startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE0);
                    }
                });
                viewHolder.setOnClickListener(R.id.tv_goods, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickPosition = position;
                        startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE1);
                    }
                });
                viewHolder.setOnClickListener(R.id.tv_num, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new EditeDialog(mContext, stockGoodsEntity.getNum() + "", 1, "请输入", new EditeDialog.EditInterface() {
                            @Override
                            public void onClick(String s) {
                                viewHolder.setText(R.id.tv_num, Strings.getString(s));
                                stockGoodsEntity.setNum(Strings.getInt(s));
                            }
                        });
                    }
                });
                viewHolder.setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDataList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
        rvData.setLayoutManager(new LinearLayoutManager(mContext));
        rvData.addItemDecoration(new RecyclerViewDivider(LinearLayoutManager.HORIZONTAL, 0.5, ContextCompat.getColor(mContext, R.color.color_divider)));
        rvData.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE0 && resultCode == Constant.RESULT_CODE0) {//扫描的库位
            String scanResult = data.getStringExtra("scanResult");
            setStock(scanResult);
        } else if (requestCode == Constant.REQUEST_CODE1 && resultCode == Constant.RESULT_CODE0) {//扫描的商品
            String scanResult = data.getStringExtra("scanResult");
            setGoods(scanResult);
        }
    }

    private void setStock(String result) {
        mDataList.get(clickPosition).setStock(result);
        mDataList.get(clickPosition).setGoods("");
        mDataList.get(clickPosition).setNum(0);
        mAdapter.notifyDataSetChanged();
    }

    private void setGoods(String result) {
        StockGoodsEntity stockGoodsEntity = mDataList.get(clickPosition);
        stockGoodsEntity.setGoods(result);
        stockGoodsEntity.setNum(stockGoodsEntity.getNum() + 1);
        mAdapter.notifyDataSetChanged();
    }


}
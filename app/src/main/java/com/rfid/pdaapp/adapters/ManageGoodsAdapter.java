package com.rfid.pdaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.goods.XhLhActivity;
import com.rfid.pdaapp.utils.CommonUtil;
import com.rfid.pdaapp.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.Map;

public class ManageGoodsAdapter extends CommonAdapter<Map<String, Object>> {
    public ManageGoodsAdapter(Context context, int layoutId, List<Map<String, Object>> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Map<String, Object> stringObjectMap, int i) {
        viewHolder.setText(R.id.tv_fbillno, Strings.getString(stringObjectMap.get("FBillNo")));
        viewHolder.setText(R.id.tv_fname, Strings.getString(stringObjectMap.get("F_HFL_SCH.FNAME")));
        viewHolder.setText(R.id.tv_fcreatedate, Strings.getString(stringObjectMap.get("FCREATEDATE")));
        viewHolder.setOnClickListener(R.id.tv_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtil.showToast("开发中......");
            }
        });
        viewHolder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, XhLhActivity.class);
                intent.putExtra("FID",Strings.getString(stringObjectMap.get("FID")));
                intent.putExtra("FBillNo",Strings.getString(stringObjectMap.get("FBillNo")));
                intent.putExtra("F_HFL_SCH.FNAME",Strings.getString(stringObjectMap.get("F_HFL_SCH.FNAME")));
                intent.putExtra("F_HFL_SCH.FNUMBER",Strings.getString(stringObjectMap.get("F_HFL_SCH.FNUMBER")));
                mContext.startActivity(intent);
            }
        });
    }
}

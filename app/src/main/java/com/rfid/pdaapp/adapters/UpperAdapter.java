package com.rfid.pdaapp.adapters;

import android.content.Context;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.Constant;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;
import java.util.Map;

public class UpperAdapter extends CommonAdapter<Map<String, Object>> {
    private int type;

    public UpperAdapter(Context context, int layoutId, List<Map<String, Object>> datas, int type) {
        super(context, layoutId, datas);
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder holder, Map<String, Object> stringObjectMap, int position) {
        if (type == Constant.UPPER_PTSJ) {
            holder.setText(R.id.tv_operator, "操作人");
        }
    }
}

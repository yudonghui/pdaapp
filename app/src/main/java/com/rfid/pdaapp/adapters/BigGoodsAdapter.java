package com.rfid.pdaapp.adapters;

import android.content.Context;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by ydh on 2022/1/13
 */
public class BigGoodsAdapter extends CommonAdapter<Map<String, Object>> {
    public BigGoodsAdapter(Context context, int layoutId, List<Map<String, Object>> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, Map<String, Object> item, int position) {

    }
}

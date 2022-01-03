package com.rfid.pdaapp.adapters;


import android.content.Context;
import android.view.View;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.callback.PositionInerface;
import com.rfid.pdaapp.entitys.StockIndexEntity;
import com.rfid.pdaapp.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class StockIndexAdapter extends CommonAdapter<StockIndexEntity> {
    PositionInerface mListener;

    public StockIndexAdapter(Context context, int layoutId, List<StockIndexEntity> datas, PositionInerface mListener) {
        super(context, layoutId, datas);
        this.mListener = mListener;
    }

    @Override
    protected void convert(ViewHolder holder, StockIndexEntity stockIndexEntity, int position) {
        holder.setText(R.id.tv_stock_name, Strings.getStringL(stockIndexEntity.getFName()));
        holder.setOnClickListener(R.id.tv_stock_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClick(position);
            }
        });
    }
}

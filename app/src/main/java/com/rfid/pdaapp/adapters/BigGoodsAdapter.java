package com.rfid.pdaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.puthouse.OutStockActivity;
import com.rfid.pdaapp.utils.Strings;
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

    /**
     * map.put("tv_goods_code", "RK21221123321");
     * map.put("tv_goods_name", "上海市闵行区九号线上海大学  销售单号：");
     * map.put("tv_bill_num", "XD5218625221");
     * map.put("tv_time", "2021-12-30 15:12:30");
     * map.put("tv_operator", "test01");
     * map.put("tv_receive_time", "2021-12-30 15:12:30");
     * map.put("status", 1 + i);
     */
    @Override
    protected void convert(ViewHolder viewHolder, Map<String, Object> item, int position) {
        viewHolder.setText(R.id.tv_goods_code, Strings.getString(item.get("tv_goods_code")));
        viewHolder.setText(R.id.tv_goods_name, Strings.getString(item.get("tv_goods_name")));
        viewHolder.setText(R.id.tv_bill_num, Strings.getString(item.get("tv_bill_num")));
        viewHolder.setText(R.id.tv_time, Strings.getString(item.get("tv_time")));
        int status = (int) item.get("status");
        if (status == 2) {
            viewHolder.setVisible(R.id.tv_operator, false);
            viewHolder.setVisible(R.id.tv_receive_time, false);
            viewHolder.setVisible(R.id.tv_receive_task, true);
        } else {
            viewHolder.setVisible(R.id.tv_operator, true);
            viewHolder.setVisible(R.id.tv_receive_time, true);
            viewHolder.setVisible(R.id.tv_receive_task, false);
            viewHolder.setText(R.id.tv_operator, "操作人：" + Strings.getString(item.get("tv_operator")));
            viewHolder.setText(R.id.tv_receive_time, "接收时间：" + Strings.getString(item.get("tv_receive_time")));
        }
        viewHolder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, OutStockActivity.class));
            }
        });
    }
}

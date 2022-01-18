package com.rfid.pdaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.upper.UpperDetailActivity;
import com.rfid.pdaapp.common.Constant;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.Map;

public class UpperAdapter extends CommonAdapter<Map<String, Object>> {
    private int type;
    private int meOrOther;//0我的，1其他

    public UpperAdapter(Context context, int layoutId, List<Map<String, Object>> datas, int type, int meOrOther) {
        super(context, layoutId, datas);
        this.type = type;
        this.meOrOther = meOrOther;
    }

    @Override
    protected void convert(ViewHolder holder, Map<String, Object> stringObjectMap, int position) {
        TextView mTvBtn = holder.getView(R.id.tv_btn);
        if (meOrOther == 0) {//我的
            holder.setVisible(R.id.tv_operator, true);
            holder.setVisible(R.id.tv_time, true);
            mTvBtn.setText("完成任务");
            mTvBtn.setBackgroundResource(R.drawable.shape_red_20);
        } else {//其他
            holder.setVisible(R.id.tv_operator, false);
            holder.setVisible(R.id.tv_time, false);
            mTvBtn.setText("接收任务");
            mTvBtn.setBackgroundResource(R.drawable.shape_theme_20);
        }
        if (type == Constant.UPPER_PTSJ) {
            holder.setVisible(R.id.ll_school, false);
        } else {
            holder.setVisible(R.id.ll_school, true);
        }
        holder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, UpperDetailActivity.class));
            }
        });
    }
}

package com.rfid.pdaapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.activity.goods.XhLhActivity;
import com.rfid.pdaapp.callback.PdaTwoInterface;
import com.rfid.pdaapp.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.Map;

public class ManageGoodsAdapter extends CommonAdapter<Map<String, Object>> {
    private int meOrOther;
    private PdaTwoInterface mListener;

    public ManageGoodsAdapter(Context context, int layoutId, List<Map<String, Object>> datas, int meOrOther) {
        super(context, layoutId, datas);
        this.meOrOther = meOrOther;
    }

    public void setListener(PdaTwoInterface mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Map<String, Object> stringObjectMap, int position) {
        TextView mTvFinishTask = viewHolder.getView(R.id.tv_finish_task);
        TextView mTvReceiveTask = viewHolder.getView(R.id.tv_receive_task);
        viewHolder.setText(R.id.tv_fbillno, Strings.getString(stringObjectMap.get("FBillNo")));
        viewHolder.setText(R.id.tv_fname, Strings.getString(stringObjectMap.get("F_HFL_SCH.FNAME")));
        viewHolder.setText(R.id.tv_fcreatedate, Strings.getString(stringObjectMap.get("FCREATEDATE")));
        viewHolder.setText(R.id.tv_operator, "操作人：" + Strings.getStringSn(stringObjectMap.get("F_BRE_ZDRUSERID.FNAME")));
        viewHolder.setText(R.id.tv_time, "接收时间：" + Strings.getStringSn(stringObjectMap.get("F_HFL_PDAZDSJ")));
        if (meOrOther == 0) {
            mTvFinishTask.setVisibility(View.VISIBLE);
            mTvReceiveTask.setVisibility(View.GONE);
        } else if (meOrOther == 1) {
            mTvFinishTask.setVisibility(View.GONE);
            mTvReceiveTask.setVisibility(View.VISIBLE);
        }

        viewHolder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, XhLhActivity.class);
                intent.putExtra("FID", Strings.getString(stringObjectMap.get("FID")));
                intent.putExtra("FBillNo", Strings.getString(stringObjectMap.get("FBillNo")));
                intent.putExtra("F_HFL_SCH.FNAME", Strings.getString(stringObjectMap.get("F_HFL_SCH.FNAME")));
                intent.putExtra("F_HFL_SCH.FNUMBER", Strings.getString(stringObjectMap.get("F_HFL_SCH.FNUMBER")));
                mContext.startActivity(intent);
            }
        });
        mTvFinishTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.clickOne(position);
            }
        });
        mTvReceiveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.clickTwo(position);
            }
        });
    }
}

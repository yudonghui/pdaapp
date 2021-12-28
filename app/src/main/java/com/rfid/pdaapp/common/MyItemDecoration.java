package com.rfid.pdaapp.common;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.utils.DisplayUtil;

/**
 * Created by ydh on 2021/12/28
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {
    public MyItemDecoration() {

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0, DisplayUtil.dp2px(10), DisplayUtil.dp2px(10), 0);
    }
}

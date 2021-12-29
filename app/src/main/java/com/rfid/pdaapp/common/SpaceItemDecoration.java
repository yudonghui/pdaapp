package com.rfid.pdaapp.common;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rfid.pdaapp.utils.DisplayUtil;

/**
 * Created by ydh on 2021/12/28
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int right = 0;
    private int bottom = 0;

    public SpaceItemDecoration(int right, int bottom) {
        this.right = DisplayUtil.dp2px(right);
        this.bottom = DisplayUtil.dp2px(bottom);
    }

    public SpaceItemDecoration(int bottom) {
        this.bottom = DisplayUtil.dp2px(bottom);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.set(0, 0, right, bottom);
    }
}

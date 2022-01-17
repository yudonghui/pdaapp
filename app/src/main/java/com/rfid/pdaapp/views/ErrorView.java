package com.rfid.pdaapp.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.utils.Strings;

/**
 * Created by ydh on 2022/1/13
 */
public class ErrorView extends LinearLayout {

    private View mInflate;
    private TextView mTvHintView;
    public Context mContext;

    private int hintTextColor;//提示文字颜色
    private String hintText;//提示文字
    private View.OnClickListener mListener;

    public void setListener(View.OnClickListener mListener) {
        this.mListener = mListener;
    }

    public ErrorView(Context context) {
        this(context, null);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflate = View.inflate(context, R.layout.error_view, this);
        mTvHintView = mInflate.findViewById(R.id.tv_hint_view);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ErrorView);
        if (typedArray != null) {
            hintTextColor = typedArray.getColor(R.styleable.ErrorView_hint_text_color, getResources().getColor(R.color.gray_txt));
            hintText = typedArray.getString(R.styleable.ErrorView_hint_text);
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setHint(hintText, hintTextColor);
        addListener();
    }

    private void setHint(String hintText, int hintTextColor) {
        mTvHintView.setText(Strings.getString(hintText));
        mTvHintView.setTextColor(hintTextColor);
    }

    private void addListener() {
        mTvHintView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onClick(v);
            }
        });
    }
}

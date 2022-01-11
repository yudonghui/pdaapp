package com.rfid.pdaapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.utils.Strings;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitleSearchBar extends LinearLayout {
    @BindView(R.id.iv_back)
    public ImageView ivBack;
    @BindView(R.id.search_edit)
    public ClearEditText searchEdit;
    @BindView(R.id.tv_search)
    public TextView tvSearch;
    @BindView(R.id.ll_title_search_bar)
    LinearLayout llTitleSearchBar;
    private View mInflate;
    public Context mContext;
    private TextListener mTvListener;

    public void setListener(TextListener mTvListener) {
        this.mTvListener = mTvListener;
    }


    public TitleSearchBar(Context context) {
        this(context, null);
    }

    public TitleSearchBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleSearchBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflate = View.inflate(context, R.layout.title_search_bar, this);
        ButterKnife.bind(mInflate);
        init(attrs, defStyleAttr);
    }

    private int default_bg = getResources().getColor(R.color.color_theme);
    private int default_right_text_color = getResources().getColor(R.color.white);
    private int backgroundColor = default_bg;//背景颜色，默认白色
    private int rightTextColor = default_right_text_color;//右边文字的颜色，默认白色
    private String rightText = "搜索";//右边的文字 默认搜索


    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleSearchBar);
        if (typedArray != null) {
            backgroundColor = typedArray.getColor(R.styleable.TitleSearchBar_background_color, default_bg);
            rightTextColor = typedArray.getColor(R.styleable.TitleSearchBar_right_text_color, default_right_text_color);
            rightText = typedArray.getString(R.styleable.TitleSearchBar_right_text);
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackground(backgroundColor);
        setTvRight(rightText, rightTextColor);
        addListener();
    }

    private void addListener() {
        if (ivBack != null) {
            ivBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                }
            });
        }
        if (tvSearch != null) {
            tvSearch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTvListener != null) {
                        mTvListener.onClick(tvSearch);
                    }
                }
            });
        }
    }

    public void setBackground(int backgroundColor) {
        llTitleSearchBar.setBackgroundColor(backgroundColor);
    }

    public void setHint(String hint) {
        searchEdit.setHint(Strings.getString(hint));
    }

    public String getText() {
        return searchEdit.getText().toString();
    }

    public void setTvRight(String rightText) {
        setTvRight(rightText, default_right_text_color);
    }

    public void setTvRight(String rightText, int rightTextColor) {
        if (!TextUtils.isEmpty(rightText)) {
            tvSearch.setVisibility(VISIBLE);
            tvSearch.setTextColor(rightTextColor);
            tvSearch.setText(rightText);
        } else {
            tvSearch.setVisibility(GONE);
        }
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener mListener) {
        searchEdit.setOnEditorActionListener(mListener);
    }

    public interface TextListener {
        void onClick(TextView textView);
    }

}

package com.rfid.pdaapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.utils.Strings;

public class TitleSearchBar extends LinearLayout {
    public ImageView ivBack;
    public ClearEditText searchEdit;
    public TextView tvSearch;
    LinearLayout llTitleSearchBar;
    private View mInflate;
    public Context mContext;
    private TextListener mTvListener;
    private EditeListener mEtListener;

    public void setListener(TextListener mTvListener) {
        this.mTvListener = mTvListener;
    }

    public void addTextChangedListener(EditeListener mEtListener) {
        this.mEtListener = mEtListener;
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
        ivBack = mInflate.findViewById(R.id.iv_back);
        searchEdit = mInflate.findViewById(R.id.search_edit);
        tvSearch = mInflate.findViewById(R.id.tv_search);
        llTitleSearchBar = mInflate.findViewById(R.id.ll_title_search_bar);
        init(attrs, defStyleAttr);
    }

    private int default_bg = getResources().getColor(R.color.color_theme);
    private int default_right_text_color = getResources().getColor(R.color.white);
    private int backgroundColor = default_bg;//背景颜色，默认白色
    private int rightTextColor = default_right_text_color;//右边文字的颜色，默认白色
    private String rightText;//右边的文字 默认搜索


    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleSearchBar);
        if (typedArray != null) {
            backgroundColor = typedArray.getColor(R.styleable.TitleSearchBar_background_color, default_bg);
            rightTextColor = typedArray.getColor(R.styleable.TitleSearchBar_right_text_color, default_right_text_color);
            rightText = TextUtils.isEmpty(typedArray.getString(R.styleable.TitleSearchBar_right_text)) ? "搜索" : typedArray.getString(R.styleable.TitleSearchBar_right_text);
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
        if (searchEdit != null) {
            searchEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (mEtListener != null) {
                        mEtListener.afterTextChanged(s.toString());
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

    public interface EditeListener {
        void afterTextChanged(String s);
    }
}

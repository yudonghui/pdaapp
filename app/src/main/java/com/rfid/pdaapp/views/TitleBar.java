package com.rfid.pdaapp.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rfid.pdaapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TitleBar extends LinearLayout {
    @BindView(R.id.image_titlebar_back)
    public ImageView mImageTitlebarBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.tv_right)
    public TextView mTvRight;
    @BindView(R.id.iv_right_second)
    public ImageView mIvRightSecond;
    @BindView(R.id.iv_right_first)
    public ImageView mIvRightFirst;
    @BindView(R.id.rl_container)
    RelativeLayout mRlContainer;
    private View mInflate;
    public Context mContext;
    private TextListener mTvListener;
    private ImageListener mIvBackListener;
    private ImageListener mIvFirstListener;
    private ImageListener mIvSecondListener;

    public void setListener(TextListener mTvListener) {
        this.mTvListener = mTvListener;
    }

    public void setListener(TextListener mTvListener, ImageListener mIvBackListener) {
        this.mTvListener = mTvListener;
        this.mIvBackListener = mIvBackListener;
    }

    public void setListener(ImageListener mIvFirstListener, TextListener mTvListener) {
        this.mIvFirstListener = mIvFirstListener;
        this.mTvListener = mTvListener;
    }

    public void setListener(TextListener mTvListener, ImageListener mIvBackListener, ImageListener mIvFirstListener, ImageListener mIvSecondListener) {
        this.mTvListener = mTvListener;
        this.mIvBackListener = mIvBackListener;
        this.mIvFirstListener = mIvFirstListener;
        this.mIvSecondListener = mIvSecondListener;
    }

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflate = View.inflate(context, R.layout.titlebar, this);
        ButterKnife.bind(mInflate);
        init(attrs, defStyleAttr);
    }

    private int default_bg = getResources().getColor(R.color.white);
    private int default_title_color = getResources().getColor(R.color.black);
    private int default_right_text_color = getResources().getColor(R.color.black);
    private int backgroundColor = default_bg;//标题颜色，默认白色
    private int titleColor = default_title_color;//标题颜色，默认白色
    private int rightTextColor = default_right_text_color;//右边文字的颜色，默认白色
    private String title;//标题
    private String rightText;//右边的文字
    private int rightImage = 0;//右边数第一个图片
    private int rightImageTwo = 0;//右边数第二个图片


    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        if (typedArray != null) {
            backgroundColor = typedArray.getColor(R.styleable.TitleBar_background_color, default_bg);
            title = typedArray.getString(R.styleable.TitleBar_title);
            titleColor = typedArray.getColor(R.styleable.TitleBar_title_color, default_title_color);
            rightTextColor = typedArray.getColor(R.styleable.TitleBar_right_text_color, default_right_text_color);

            rightText = typedArray.getString(R.styleable.TitleBar_right_text);
            rightImage = typedArray.getResourceId(R.styleable.TitleBar_right_image, 0);
            rightImageTwo = typedArray.getResourceId(R.styleable.TitleBar_right_image_two, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackground(backgroundColor);
        setTitle(title, titleColor);
        setTvRight(rightText, rightTextColor);
        setRightImage(rightImage);
        setRightImageSecond(rightImageTwo);
        addListener();
    }

    private void addListener() {
        if (mImageTitlebarBack != null) {
            mImageTitlebarBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) mContext).finish();
                }
            });
        }
        if (mTvRight != null) {
            mTvRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTvListener != null) {
                        mTvListener.onClick(mTvRight);
                    }
                }
            });
        }
        if (mIvRightFirst != null) {
            mIvRightFirst.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIvFirstListener != null) {
                        mIvFirstListener.onClick(mIvRightFirst);
                    }
                }
            });
        }
        if (mIvRightSecond != null) {
            mIvRightSecond.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIvSecondListener != null) {
                        mIvSecondListener.onClick(mIvRightSecond);
                    }
                }
            });
        }
    }

    public void setBackground(int backgroundColor) {
        mRlContainer.setBackgroundColor(backgroundColor);
    }

    public void setTitle(String title) {
        setTitle(title, default_title_color);
    }

    public void setTitle(String title, int titleColor) {
        if (!TextUtils.isEmpty(title)) {
            mTitle.setVisibility(VISIBLE);
            mTitle.setTextColor(titleColor);
            mTitle.setText(title);
        } else {
            mTitle.setVisibility(GONE);
        }
    }

    public void setTvRight(String rightText) {
        setTvRight(rightText, default_right_text_color);
    }

    public void setTvRight(String rightText, int rightTextColor) {
        if (!TextUtils.isEmpty(rightText)) {
            mTvRight.setVisibility(VISIBLE);
            mTvRight.setTextColor(rightTextColor);
            mTvRight.setText(rightText);
        } else {
            mTvRight.setVisibility(GONE);
        }
    }

    public void setRightImage(int rightImage) {
        if (rightImage == 0) {
            mIvRightFirst.setVisibility(GONE);
        } else {
            mIvRightFirst.setVisibility(VISIBLE);
            mIvRightFirst.setImageResource(rightImage);
        }
    }

    public void setRightImageSecond(int rightImageSecond) {
        if (rightImageTwo == 0) {
            mIvRightSecond.setVisibility(GONE);
        } else {
            mIvRightSecond.setVisibility(VISIBLE);
            mIvRightSecond.setImageResource(rightImageTwo);
        }
    }

    public void setLeftVisible(int leftVisible) {
        mImageTitlebarBack.setVisibility(leftVisible);
    }

    public interface TextListener {
        void onClick(TextView textView);
    }

    public interface ImageListener {
        void onClick(ImageView textView);
    }

}

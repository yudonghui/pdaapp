package com.rfid.pdaapp.dialogs;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.rfid.pdaapp.R;

/**
 * Created by ydh on 2022/1/12
 */
public class EditeDialog {
    private TextView mTitle;
    private TextView mTvCancel;
    private TextView mTvConfirm;
    private EditText mEtContent;
    private BaseDialog mHintDialog;
    private View view;

    public EditeDialog(Context mContext, String title, final EditInterface mListener) {
        this(mContext, "", title, mListener);
    }

    /**
     * @param
     */
    public EditeDialog(Context mContext, String content, String title, final EditInterface mListener) {
        mHintDialog = new BaseDialog(mContext, R.style.HintDialogInput);
        mHintDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = View.inflate(mContext, R.layout.dialog_edite, null);
        mTitle = view.findViewById(R.id.title);
        mEtContent = (EditText) view.findViewById(R.id.et_content);
        mTvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        mTvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        mEtContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        mEtContent.setSingleLine(false);//改变默认的单行模式
        mEtContent.setHorizontallyScrolling(false);//水平滚动设置为False
        mTitle.setText(title);
        if (TextUtils.isEmpty(content)) {
            mEtContent.setText("");
        } else {
            mEtContent.setText(content);
            mEtContent.setSelection(content.length());
        }
        mHintDialog.setContentView(view);
        mHintDialog.show();
        mEtContent.setFocusable(true);
        mEtContent.setFocusableInTouchMode(true);
        mEtContent.requestFocus();
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
            }
        });
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHintDialog.dismiss();
                String s = mEtContent.getText().toString();
                mListener.onClick(s);
            }
        });
    }

    public interface EditInterface {
        void onClick(String s);
    }
}

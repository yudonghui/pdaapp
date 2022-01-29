package com.rfid.pdaapp.common.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rfid.pdaapp.R;
import com.rfid.pdaapp.utils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;


/**
 *Created by ydh on 2022/1/17
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    private View view;
    protected Unbinder unbinder;
    public List<Call> mNetWorkList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, view);
        mContext = getContext();
        init();
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void init();

    protected Dialog mLoadingDialog;

    public void showLoadingDialog() {
        if (mLoadingDialog == null) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_load, null);
            mLoadingDialog = new Dialog(getActivity(), R.style.LoadDialog);

            Window window = mLoadingDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mLoadingDialog.setCanceledOnTouchOutside(true);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
            TextView mTextView = itemView.findViewById(R.id.tv_hint);
            mTextView.setVisibility(View.GONE);
            mLoadingDialog.setContentView(itemView);
        }
        mLoadingDialog.show();
    }

    public void cancelLoadingDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.dismiss();
    }

    /**
     * 跳转页面
     *
     * @param clz
     */
    protected void startActivity(Class<?> clz) {
        startActivity(new Intent(getActivity(), clz));
    }

    /**
     * 携带String的页面跳转(TAG)
     *
     * @param clz
     */
    protected void startActivity(Class<?> clz, String tag, String value) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (!TextUtils.isEmpty(value)) {
            intent.putExtra(tag, value);
        }
        startActivity(intent);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    protected void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void stopOver(SmartRefreshLayout mRefreshLayout) {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadMore();
            mRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        LogUtils.e("销毁");
        if (unbinder != null)
            unbinder.unbind();
        try {
            for (int i = 0; i < mNetWorkList.size(); i++) {
                Call call = mNetWorkList.get(i);
                call.cancel();
            }
        } catch (Exception e) {

        }
        super.onDestroyView();
    }
}
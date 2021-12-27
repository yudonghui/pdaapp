package com.rfid.pdaapp.common.base;


import android.text.TextUtils;

import com.google.gson.stream.MalformedJsonException;
import com.rfid.pdaapp.common.MyActivityManager;
import com.rfid.pdaapp.dialogs.HisDialog;
import com.rfid.pdaapp.utils.CommonUtil;
import com.rfid.pdaapp.utils.LogUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseBack<T> implements Callback<BaseEntity<T>> {
    protected abstract void onSuccess(T t);

    protected abstract void onFailed(String code, String msg);

    protected void onSuccess(BaseEntity<T> baseEntity) {
    }

    @Override
    public void onResponse(Call<BaseEntity<T>> call, Response<BaseEntity<T>> response) {
        BaseEntity<T> baseEntity = response.body();
        if (response.isSuccessful() && baseEntity != null) {
            if (baseEntity.getCode().equals("1000")) {//成功
                onSuccess(baseEntity);
                onSuccess(baseEntity.getData());
            } else {
                showDialog(baseEntity.getMsg());
                onFailed(baseEntity.getCode(), baseEntity.getMsg());
            }
        } else {
            showDialog(response.message());
            onFailed(response.code() + "", response.message());
        }
    }

    @Override
    public void onFailure(Call<BaseEntity<T>> call, Throwable t) {
        LogUtils.e("请求出错" + t.getMessage());
        if (t instanceof ConnectException) {//网络连接失败
            CommonUtil.showToast("网络开小差了，请检查网络");
            onFailed("", "");
        } else if (t instanceof MalformedJsonException) {
            CommonUtil.showToast("解析数据失败");
            onFailed("", "");
        } else if (t instanceof SocketTimeoutException) {
            CommonUtil.showToast("网络请求超时");
            onFailed("", "");
        } else if (t instanceof UnknownHostException) {
            CommonUtil.showToast("网络请求失败");
        } else if (t instanceof IOException && "Canceled".equals(t.getMessage())) {//在网络还没有结束的时候关闭了页面。取消

        } else {//网络请求关闭。或者其他异常

        }

    }

    private void showDialog(String msg) {
        new HisDialog.Builder().title("提示")
                .message(TextUtils.isEmpty(msg) ? "未知错误" : msg)
                .rightBtn("知道了")
                .build(MyActivityManager.getInstance().getCurrentActivity(), 1).show();
    }
}

package com.rfid.pdaapp.activity;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;

import com.jaeger.library.StatusBarUtil;
import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.SPUtils;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.common.network.HttpClient;
import com.rfid.pdaapp.entitys.LoginEntity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initStatusBar() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.transparent), 0);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        showLoadingDialog();
        String account = SPUtils.getCache(SPUtils.FILE_ACCOUNT, SPUtils.ACCOUNT);
        String password = SPUtils.getCache(SPUtils.FILE_ACCOUNT, SPUtils.PASSWORD);
        String kdSessionId = SPUtils.getCache(SPUtils.FILE_USER, SPUtils.KD_SESSIONID);
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password) || TextUtils.isEmpty(kdSessionId)) {
            startActivity(LoginActivity.class);
            finish();
        } else {
            login(account, password);
        }
    }

    private void login(String account, String password) {
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("acctID", "61b9b54d631462");
        paramsMap.put("username", account);
        paramsMap.put("password", password);
        paramsMap.put("lcid", "2052");
        Call<LoginEntity> call = HttpClient.getHttpApi().login(paramsMap);
        mNetWorkList.add(call);
        call.enqueue(new Callback<LoginEntity>() {
            @Override
            public void onResponse(Call<LoginEntity> call, Response<LoginEntity> response) {
                cancelLoadingDialog();
                if (response != null && response.isSuccessful() && response.body() != null) {
                    LoginEntity body = response.body();
                    int loginResultType = body.getLoginResultType();
                    if (loginResultType == 1) {
                        SPUtils.setCache(SPUtils.FILE_USER, SPUtils.KD_SESSIONID, body.getKDSVCSessionId());
                        startActivity(MainActivity.class);
                    } else if (loginResultType == 0) {
                        startActivity(LoginActivity.class);
                    } else {
                        startActivity(LoginActivity.class);
                    }
                } else {
                    startActivity(LoginActivity.class);
                }
                finish();
            }

            @Override
            public void onFailure(Call<LoginEntity> call, Throwable t) {
                cancelLoadingDialog();
                startActivity(LoginActivity.class);
                finish();
            }
        });
    }
}
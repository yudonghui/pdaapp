package com.rfid.pdaapp.activity;

import android.Manifest;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rfid.pdaapp.BuildConfig;
import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.SPUtils;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.common.network.HttpClient;
import com.rfid.pdaapp.entitys.LoginEntity;
import com.rfid.pdaapp.utils.CommonUtil;
import com.rfid.pdaapp.views.ClearEditText;
import com.rfid.pdaapp.views.TitleBar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.account_edit)
    ClearEditText accountEdit;
    @BindView(R.id.password_edit)
    EditText passwordEdit;
    @BindView(R.id.iv_password)
    ImageView ivPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    private boolean isShowPassword = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        requestPermission(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        }, 1);
        tvVersion.setText("v " + BuildConfig.VERSION_NAME);
    }


    @OnClick({R.id.iv_password, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_password:
                if (isShowPassword) {
                    passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivPassword.setImageResource(R.mipmap.close_eye);
                    isShowPassword = false;
                } else {
                    passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivPassword.setImageResource(R.mipmap.open_eye);
                    isShowPassword = true;
                }
                break;
            case R.id.tv_login:
                //startActivity(HWScanActivity.class);
                login();
                break;
        }
    }

    private void login() {
        String username = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if (TextUtils.isEmpty(username)) {
            CommonUtil.showToast("请输入账户");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            CommonUtil.showToast("请输入密码");
            return;
        }
        HashMap<String, String> paramsMap = new HashMap<>();
        // paramsMap.put("acctID", "61b9b54d631462");
        paramsMap.put("acctID", "6172cc88ff4604");
        paramsMap.put("username", username);
        paramsMap.put("password", password);
        paramsMap.put("lcid", "2052");
        Call<LoginEntity> call = HttpClient.getHttpApi().login(paramsMap);
        mNetWorkList.add(call);
        call.enqueue(new Callback<LoginEntity>() {
            @Override
            public void onResponse(Call<LoginEntity> call, Response<LoginEntity> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    LoginEntity body = response.body();
                    int loginResultType = body.getLoginResultType();
                    if (loginResultType == 1) {
                       SPUtils.setUserInfo(body,username,password);
                        startActivity(MainActivity.class);
                        finish();
                    } else if (loginResultType == 0) {
                        CommonUtil.showToast("用户或密码错误");
                    } else {
                        CommonUtil.showToast(body.getMessage());
                    }
                } else {
                    CommonUtil.showToast("登录失败");
                }
            }

            @Override
            public void onFailure(Call<LoginEntity> call, Throwable t) {
                CommonUtil.showToast("登录失败");
            }
        });
    }
}

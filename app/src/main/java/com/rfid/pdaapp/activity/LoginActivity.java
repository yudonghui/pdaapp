package com.rfid.pdaapp.activity;

import android.Manifest;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rfid.pdaapp.BuildConfig;
import com.rfid.pdaapp.R;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.views.ClearEditText;
import com.rfid.pdaapp.views.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

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
    protected void init() {
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
                startActivity(MainActivity.class);
                break;
        }
    }
}

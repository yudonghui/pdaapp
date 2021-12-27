package com.rfid.pdaapp.common.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rfid.pdaapp.common.NetBroadcastReceiver;
import com.rfid.pdaapp.utils.LogUtils;
import com.rfid.pdaapp.utils.NetWorkUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ydh on 2021/12/24
 */
public abstract class SimpleActivity extends AppCompatActivity implements NetBroadcastReceiver.NetWorkCallBack {
    protected Context mContext;
    private Unbinder mUnbinder;
    private NetBroadcastReceiver mNetBroadcastReceiver;
    /**
     * 网络类型
     */
    private int netMobile;

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        //动态接受网络变化的广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mNetBroadcastReceiver = new NetBroadcastReceiver(this);
        registerReceiver(mNetBroadcastReceiver, intentFilter);
    }

    protected abstract void init();

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 带返回通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 带返回含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetWorkUtils.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetWorkUtils.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetWorkUtils.NETWORK_NONE) {
            return false;
        }
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogUtils.e("生命周期：onNewIntent");
        super.onNewIntent(intent);
    }

    @Override
    protected void onStart() {
        LogUtils.e("生命周期：onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        LogUtils.e("生命周期：onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        LogUtils.e("生命周期：onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        LogUtils.e("生命周期：onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        LogUtils.e("生命周期：onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.e("生命周期：onDestroy");
        unregisterReceiver(mNetBroadcastReceiver);
        mUnbinder.unbind();
        super.onDestroy();
    }
}

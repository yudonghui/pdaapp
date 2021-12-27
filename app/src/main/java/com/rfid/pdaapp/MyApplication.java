package com.rfid.pdaapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.rfid.pdaapp.common.MyActivityManager;

/**
 * Created by ydh on 2021/12/24
 */
public class MyApplication extends Application {
    private static Context mContext;
    public static boolean ISLOG = true;//false正式环境，true测试环境

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //监听activity生命周期，方便回头获取栈顶的activity
        registerActivity();
    }

    public static Context getContext() {
        return mContext;
    }

    private void registerActivity() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActivityManager.getInstance().setCurrentActivity(activity);

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

}

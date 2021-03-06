package com.rfid.pdaapp.common;

import android.content.SharedPreferences;

import com.rfid.pdaapp.MyApplication;
import com.rfid.pdaapp.entitys.LoginEntity;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by zhengluping on 2017/12/28.
 * 缓存
 */
public class SPUtils {

    //用户账号文件
    public static final String FILE_ACCOUNT = "cache_account";
    public static final String ACCOUNT = "account"; //用户账户
    public static final String PASSWORD = "password"; //用户密码

    //用户信息文件
    public static final String FILE_USER = "cache_user";
    public static final String TOKEN = "token"; //用户token
    public static final String KD_SESSIONID = "kdservice-sessionid"; //
    public static final String USER_ID = "user_id"; //
    public static final String USER_NAME = "user_name"; //

    /**
     * 用于存储String类型的数据
     *
     * @param fileNaem 缓存的文件名
     * @param spNaem   缓存数据的key
     * @param spValue  缓存数据的值
     */
    public static void setCache(String fileNaem, String spNaem, String spValue) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(spNaem, spValue);
        editor.commit();
    }

    /**
     * 用于存储String类型的数据
     *
     * @param fileNaem 缓存的文件名
     * @param spNaem   缓存数据的key
     * @param spValue  缓存数据的值
     */
    public static void setCache(String fileNaem, String[] spNaem, String[] spValue) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < spNaem.length; i++) {
            editor.putString(spNaem[i], spValue[i]);
        }
        editor.commit();
    }

    /**
     * 获取缓存值
     *
     * @param fileNaem
     * @param spNaem
     * @return 缓存数据的值
     */
    public static String getCache(String fileNaem, String spNaem) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        String spValue = sp.getString(spNaem, "");
        return spValue;
    }

    public static void clearCache(String fileName) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(fileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存客户登录人信息
     */
    public static void setUserInfo(LoginEntity body, String username, String password) {
        SPUtils.setCache(SPUtils.FILE_ACCOUNT, SPUtils.ACCOUNT, username);
        SPUtils.setCache(SPUtils.FILE_ACCOUNT, SPUtils.PASSWORD, password);
        SPUtils.setCache(SPUtils.FILE_USER, SPUtils.KD_SESSIONID, body.getKDSVCSessionId());
        SPUtils.setCache(SPUtils.FILE_USER, SPUtils.USER_ID, body.getContext().getUserId());
        SPUtils.setCache(SPUtils.FILE_USER, SPUtils.USER_NAME, body.getContext().getUserName());
    }
}

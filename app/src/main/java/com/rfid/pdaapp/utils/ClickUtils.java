package com.rfid.pdaapp.utils;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class ClickUtils {
    // if (ClickUtils.isFastClick()) return;

    private static long lastClickTime;

    public synchronized static boolean isFastClickShort() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isFastClickLong() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}

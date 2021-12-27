package com.rfid.pdaapp.utils;

import android.content.Context;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.rfid.pdaapp.MyApplication;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 工具类
 */
public class CommonUtil {

    private static Toast toast;

    /**
     * 解决Toast重复弹出 长时间不消失的问题
     *
     * @param message
     */
    public static void showToast(String message) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }


    /**
     * 相加
     *
     * @param decimals 小数点数
     */
    public static String add(String num1, String num2, int decimals) {
        BigDecimal bigDecimal1 = new BigDecimal(TextUtils.isEmpty(num1) ? "0" : num1);
        BigDecimal bigDecimal2 = new BigDecimal(TextUtils.isEmpty(num2) ? "0" : num2);
        BigDecimal bigDecimal = bigDecimal1.add(bigDecimal2);
        return bigDecimal.setScale(decimals, BigDecimal.ROUND_HALF_DOWN).toString();
    }

    /**
     * 相加
     * 默认保留两位小数
     */
    public static String add(String num1, String num2) {
        return add(num1, num2, 2);
    }

    /**
     * 相乘
     *
     * @param decimals 小数点数
     */
    public static String mul(String num1, String num2, int decimals) {
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        BigDecimal bigDecimal = bigDecimal1.multiply(bigDecimal2);
        return bigDecimal.setScale(decimals, BigDecimal.ROUND_HALF_DOWN).toString();
    }

    /**
     * 相乘
     * 默认保留两位小数
     */
    public static String mul(String num1, String num2) {
        return mul(num1, num2, 2);
    }

    /**
     * @param decimals 小数点数
     */
    public static String getNnmber(String numberStr, int decimals) {
        if (TextUtils.isEmpty(numberStr)) return "0";
        BigDecimal bigDecimal = new BigDecimal(numberStr);
        return bigDecimal.setScale(decimals, BigDecimal.ROUND_HALF_DOWN).toString();
    }

    //-------------------------------------------------------键盘的显示隐藏-------------------------------------------------------

    //根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
    public static boolean isShouldHideInput(View v, MotionEvent event, int a) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                if (a == 0) {
                    isShouldHideInput(v, event, 1);
                }
                return true;
            }
        }
        return false;
    }

    //多种隐藏软件盘方法的其中一种
    public static void hideSoftInput(IBinder token, Context context) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    //如果软键盘在窗口上已经显示，则隐藏，反之则显示
    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void removeEmpty(Map<String, Set<String>> selectedValue) {
        if (selectedValue != null && selectedValue.size() > 0) {
            Iterator<Map.Entry<String, Set<String>>> iterator = selectedValue.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Set<String>> entry = iterator.next();
                Set<String> value = entry.getValue();
                if (value == null || value.size() == 0) {
                    iterator.remove();
                }
            }
        }
    }
}

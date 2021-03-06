package com.rfid.pdaapp.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import com.blankj.utilcode.util.ObjectUtils;
import com.rfid.pdaapp.common.ErrorsEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Strings {
    public static String getString(String string) {
        return TextUtils.isEmpty(string) ? "" : string;
    }

    public static String getString(Object string) {
        if (string == null) return "";
        if (string instanceof String)
            return (String) string;
        return "";
    }
    public static String getStringSn(Object string) {
        if (string == null) return "无";
        if (string instanceof String)
            return (String) string;
        return "无";
    }
    public static String getStringS(String string) {
        return TextUtils.isEmpty(string) ? "暂无" : string;
    }

    public static String getStringSn(String string) {
        return TextUtils.isEmpty(string) ? "无" : string;
    }

    public static String getStringL(String string) {
        return TextUtils.isEmpty(string) ? "-" : string;
    }

    public static String getStringL(Object string) {
        if (string == null) return "-";
        if (string instanceof String)
            return TextUtils.isEmpty((String) string) ? "-" : (String) string;
        else if (string instanceof Double) {
            return (int) (Double.parseDouble(string + "")) + "";
        }
        return string.toString();
    }

    public static String getStringN(String string) {
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return "";
        }
        return string;
    }

    public static String getSecretPhone(String string) {
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return "";
        }
        if (string.length() != 11) return string;
        return string.substring(0, 3) + "****" + string.substring(7);
    }

    public static String getString0(String string) {
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return "0";
        }
        return string;
    }

    public static boolean isEmty0(String string) {
        if (TextUtils.isEmpty(string) || "0".equals(string)) {
            return true;
        }
        return false;
    }

    public static int getIntByString(String string) {
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return 0;
        }
        return Integer.parseInt(string);
    }

    public static int getIntNo0(String string) {
        if (TextUtils.isEmpty(string) || "null".equals(string) || "0".equals(string)) {
            return 1;
        }
        try {
            int anInt = Integer.parseInt(string);
            return anInt;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * @param str1 是否相等
     */
    public static boolean equals(String str1, String str2) {
        if (!TextUtils.isEmpty(str1)) {
            return str1.equals(str2);
        } else {
            if (TextUtils.isEmpty(str2)) {
                return true;//str1和str2都是空
            } else {
                return false;//str1空 str2不为空
            }
        }
    }

    public static boolean equals(String str1, String str2, String str3) {
        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3))
            return false;
        return str1.equals(str2) && str1.equals(str3);
    }

    public static double getDouble(String string) {
        if (TextUtils.isEmpty(string)) return 0;
        try {
            double doub = Double.parseDouble(string);
            return doub;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getInt(String string) {
        if (TextUtils.isEmpty(string)) return 0;
        try {
            int anInt = Integer.parseInt(string);
            return anInt;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getInt(Object string) {
        if (ObjectUtils.isEmpty(string)) return 0;
        try {
            if (string instanceof Double) {
                return (int) (double) string;
            }
            if (string instanceof Integer)
                return (int) string;
            if (string instanceof String)
                return Integer.parseInt((String) string);
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getIntMax(String string) {
        if (TextUtils.isEmpty(string) || "0".equals(string)) return 100000;
        try {
            int anInt = Integer.parseInt(string);
            return anInt;
        } catch (Exception e) {
            return 0;
        }
    }


    public static SpannableString getSpannable(String text1, int resId1, String text2, int resId2) {
        String content = text1 + text2;
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(resId1), 0, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(resId2), text1.length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static String getStrByInt(int aint) {
        if (aint < 10) {
            return "0" + aint;
        } else {
            return aint + "";
        }
    }

    public static String getStrByList(List<String> list) {
        if (list == null || list.size() == 0) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i) + ",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static String getStrByArray(String[] array) {
        if (array == null || array.length == 0) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(array[i] + ",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static List<Map<String, Object>> getListMap(List<List<Object>> list, String[] array) {
        if (list == null || array == null) return new ArrayList<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            mapList.add(getMap(list.get(i), array));
        }
        return mapList;
    }

    public static Map<String, Object> getMap(List<Object> list, String[] array) {
        if (list == null || array == null) return new HashMap<>();
        int total = list.size() > array.length ? array.length : list.size();
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < total; i++) {
            map.put(array[i], list.get(i));
        }
        return map;
    }

    public static String getErrorMessage(List<ErrorsEntity> list) {
        if (list == null || list.size() == 0) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(list.get(i).getMessage() + "\n");
        }
        return stringBuilder.toString();
    }
}

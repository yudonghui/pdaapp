package com.rfid.pdaapp.entitys;

/**
 * Created by ydh on 2021/12/28
 */
public class HomeEntity {
    private String content;
    private int type;
    private int resId;

    public HomeEntity(String content, int type,int resId) {
        this.content = content;
        this.type = type;
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

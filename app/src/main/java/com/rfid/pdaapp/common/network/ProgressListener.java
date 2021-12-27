package com.rfid.pdaapp.common.network;

/**
 * Created by ydh on 2021/9/3
 */
public interface ProgressListener {
    void onProgress(long progress, long total, boolean done);
}

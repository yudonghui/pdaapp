package com.rfid.pdaapp.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class SeqHelper {

    private static AtomicInteger seq = new AtomicInteger(1);
    private static final int MOD = 10000;
    /**
     * 获取一个ID
     * @return
     */
    public static int genSeq() {
        int nextSeq = seq.getAndAdd(1);
        if(nextSeq + 1 >= MOD) {
            seq.set(0);
        }
        return MOD + nextSeq;
    }
    /**
     * 创建一个编码
     * @return
     */
    public static String genNumber(String pre) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        String dateStr = sdf.format(new Date());
        if(pre==null) {
            return String.format("%s%d", dateStr, genSeq());
        }
        return String.format("%s%s%d", pre, dateStr, genSeq());
    }

}

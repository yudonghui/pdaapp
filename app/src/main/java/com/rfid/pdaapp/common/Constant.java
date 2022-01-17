package com.rfid.pdaapp.common;

public interface Constant {
    String UPDATE_URL = "http://api.bq04.com/apps/latest/61d014bc23389f76bf4b0623?api_token=027f1320aaa8f3341bca7d7e9efa9589";
    int REQUEST_CODE0 = 100;
    int REQUEST_CODE1 = 101;
    int REQUEST_CODE2 = 102;
    int REQUEST_CODE3 = 103;
    int REQUEST_CODE4 = 104;
    int RESULT_CODE0 = 200;
    int RESULT_CODE1 = 201;
    int RESULT_CODE2 = 202;
    int RESULT_CODE3 = 203;
    int RESULT_CODE4 = 204;
    /**
     * 首页
     */
    int HOME_KCCX = 0;//库存查询
    int HOME_KWYK = 1;//库位移位
    int HOME_KWTZ = 2;//库位调整
    int HOME_SHRK = 3;//收货入库
    int HOME_KWSJ = 4;//库位上架
    /**
     * 库位上架
     */
    int UPPER_PTSJ = 0;//普通上架
    int UPPER_JKSJ = 1;//减库上架
    int UPPER_JGSJ = 2;//加工上架
    int UPPER_DHTHSJ = 3;//大货退货上架
    int UPPER_SDCSHSJ = 4;//锁定仓收货上架
}

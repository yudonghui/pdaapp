package com.rfid.pdaapp.entitys;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

public class StockIndexEntity extends BaseIndexPinyinBean {
    private String FStockId;
    private String FNumber;
    private String FName;

    public StockIndexEntity() {
    }

    public StockIndexEntity(String FStockId, String FNumber, String FName) {
        this.FStockId = FStockId;
        this.FNumber = FNumber;
        this.FName = FName;
    }

    public String getFStockId() {
        return FStockId;
    }

    public void setFStockId(String FStockId) {
        this.FStockId = FStockId;
    }

    public String getFNumber() {
        return FNumber;
    }

    public void setFNumber(String FNumber) {
        this.FNumber = FNumber;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    @Override
    public String getTarget() {
        return FName;
    }
}

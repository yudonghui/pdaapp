package com.rfid.pdaapp.entitys;

/**
 * Created by ydh on 2021/12/30
 */
public class StockEntity {
    private String size;
    private String user;
    private String realStock;
    private String boxNum;
    private String location;

    public StockEntity(String size, String user, String realStock, String boxNum, String location) {
        this.size = size;
        this.user = user;
        this.realStock = realStock;
        this.boxNum = boxNum;
        this.location = location;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRealStock() {
        return realStock;
    }

    public void setRealStock(String realStock) {
        this.realStock = realStock;
    }

    public String getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(String boxNum) {
        this.boxNum = boxNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

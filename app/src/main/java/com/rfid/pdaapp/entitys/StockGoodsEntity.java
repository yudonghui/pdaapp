package com.rfid.pdaapp.entitys;

public class StockGoodsEntity {
    private String stock;
    private String goods;
    private int num;

    public StockGoodsEntity(String stock) {
        this.stock = stock;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

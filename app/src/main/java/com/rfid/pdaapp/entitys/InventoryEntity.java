package com.rfid.pdaapp.entitys;

import java.util.List;

/**
 * Created by ydh on 2022/1/24
 */
public class InventoryEntity {
    private String code;
    private List<InventoryFormEntity> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<InventoryFormEntity> getList() {
        return list;
    }

    public void setList(List<InventoryFormEntity> list) {
        this.list = list;
    }
}

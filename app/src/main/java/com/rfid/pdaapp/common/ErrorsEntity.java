package com.rfid.pdaapp.common;

public class ErrorsEntity {
    /**
     * FieldName : FBillNo
     * Message : 违反字段唯一性要求：编码唯一。[test5]在当前系统中已经被使用。
     * DIndex : 0
     */

    private String FieldName;
    private String Message;
    private int DIndex;

    public String getFieldName() {
        return FieldName;
    }

    public void setFieldName(String FieldName) {
        this.FieldName = FieldName;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public int getDIndex() {
        return DIndex;
    }

    public void setDIndex(int DIndex) {
        this.DIndex = DIndex;
    }
}

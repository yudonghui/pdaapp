package com.rfid.pdaapp.common;

import java.util.List;

public class ResponeEntity {

    /**
     * Result : {"ResponseStatus":{"ErrorCode":500,"IsSuccess":false,"Errors":[{"FieldName":"FBillNo","Message":"违反字段唯一性要求：编码唯一。[test5]在当前系统中已经被使用。","DIndex":0}],"SuccessEntitys":[],"SuccessMessages":[],"MsgCode":11}}
     */

    private ResultBean Result;

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        /**
         * ResponseStatus : {"ErrorCode":500,"IsSuccess":false,"Errors":[{"FieldName":"FBillNo","Message":"违反字段唯一性要求：编码唯一。[test5]在当前系统中已经被使用。","DIndex":0}],"SuccessEntitys":[],"SuccessMessages":[],"MsgCode":11}
         */

        private ResponseStatusBean ResponseStatus;

        public ResponseStatusBean getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(ResponseStatusBean ResponseStatus) {
            this.ResponseStatus = ResponseStatus;
        }

        public static class ResponseStatusBean {
            /**
             * ErrorCode : 500
             * IsSuccess : false
             * Errors : [{"FieldName":"FBillNo","Message":"违反字段唯一性要求：编码唯一。[test5]在当前系统中已经被使用。","DIndex":0}]
             * SuccessEntitys : []
             * SuccessMessages : []
             * MsgCode : 11
             */

            private int ErrorCode;
            private boolean IsSuccess;
            private int MsgCode;
            private List<ErrorsEntity> Errors;
            private List<?> SuccessEntitys;
            private List<?> SuccessMessages;

            public int getErrorCode() {
                return ErrorCode;
            }

            public void setErrorCode(int ErrorCode) {
                this.ErrorCode = ErrorCode;
            }

            public boolean isIsSuccess() {
                return IsSuccess;
            }

            public void setIsSuccess(boolean IsSuccess) {
                this.IsSuccess = IsSuccess;
            }

            public int getMsgCode() {
                return MsgCode;
            }

            public void setMsgCode(int MsgCode) {
                this.MsgCode = MsgCode;
            }

            public List<ErrorsEntity> getErrors() {
                return Errors;
            }

            public void setErrors(List<ErrorsEntity> Errors) {
                this.Errors = Errors;
            }

            public List<?> getSuccessEntitys() {
                return SuccessEntitys;
            }

            public void setSuccessEntitys(List<?> SuccessEntitys) {
                this.SuccessEntitys = SuccessEntitys;
            }

            public List<?> getSuccessMessages() {
                return SuccessMessages;
            }

            public void setSuccessMessages(List<?> SuccessMessages) {
                this.SuccessMessages = SuccessMessages;
            }

        }
    }
}

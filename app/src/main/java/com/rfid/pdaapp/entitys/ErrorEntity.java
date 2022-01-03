package com.rfid.pdaapp.entitys;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorEntity {

    @SerializedName("Result")
    private ResultDTO result;

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public static class ResultDTO {
        @SerializedName("ResponseStatus")
        private ResponseStatusDTO responseStatus;

        public ResponseStatusDTO getResponseStatus() {
            return responseStatus;
        }

        public void setResponseStatus(ResponseStatusDTO responseStatus) {
            this.responseStatus = responseStatus;
        }

        public static class ResponseStatusDTO {
            @SerializedName("ErrorCode")
            private int errorCode;
            @SerializedName("IsSuccess")
            private boolean isSuccess;
            @SerializedName("Errors")
            private List<ErrorsDTO> errors;
            @SerializedName("SuccessEntitys")
            private List<?> successEntitys;
            @SerializedName("SuccessMessages")
            private List<?> successMessages;
            @SerializedName("MsgCode")
            private int msgCode;

            public int getErrorCode() {
                return errorCode;
            }

            public void setErrorCode(int errorCode) {
                this.errorCode = errorCode;
            }

            public boolean isIsSuccess() {
                return isSuccess;
            }

            public void setIsSuccess(boolean isSuccess) {
                this.isSuccess = isSuccess;
            }

            public List<ErrorsDTO> getErrors() {
                return errors;
            }

            public void setErrors(List<ErrorsDTO> errors) {
                this.errors = errors;
            }

            public List<?> getSuccessEntitys() {
                return successEntitys;
            }

            public void setSuccessEntitys(List<?> successEntitys) {
                this.successEntitys = successEntitys;
            }

            public List<?> getSuccessMessages() {
                return successMessages;
            }

            public void setSuccessMessages(List<?> successMessages) {
                this.successMessages = successMessages;
            }

            public int getMsgCode() {
                return msgCode;
            }

            public void setMsgCode(int msgCode) {
                this.msgCode = msgCode;
            }

            public static class ErrorsDTO {
                @SerializedName("FieldName")
                private Object fieldName;
                @SerializedName("Message")
                private String message;
                @SerializedName("DIndex")
                private int dIndex;

                public Object getFieldName() {
                    return fieldName;
                }

                public void setFieldName(Object fieldName) {
                    this.fieldName = fieldName;
                }

                public String getMessage() {
                    return message;
                }

                public void setMessage(String message) {
                    this.message = message;
                }

                public int getDIndex() {
                    return dIndex;
                }

                public void setDIndex(int dIndex) {
                    this.dIndex = dIndex;
                }
            }
        }
    }
}

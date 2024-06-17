package com.person.controller.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.person.infrastructure.constant.ErrorCode;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseObject {
    private boolean isSuccess = false;
    private Object data;
    private String errorCode = ErrorCode.ERRORS.toString();
    private String message;
    private String requestId;
    private Boolean isDebug = true;

    public ResponseObject() {

    }

    public <T> ResponseObject(T obj) {
        if (obj != null) {
            this.isSuccess = true;
            this.data = obj;
            this.errorCode = ErrorCode.SUCCESS.toString();
        }
    }

    public ResponseObject(String errorCode, String message, boolean isSuccess) {
        this.errorCode = errorCode;
        this.message = message;
        this.isSuccess = isSuccess;
    }
}

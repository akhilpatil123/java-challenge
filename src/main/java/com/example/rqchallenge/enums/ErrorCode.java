package com.example.rqchallenge.enums;

public enum ErrorCode {

    EXCEPTION_WHILE_CALLING_EXTERNAL_API("EMP_001"),
    NO_RECORDS_FOUND("EMP_002"),
    INVALID_INPUT("EMP_003"),
    INTERNAL_SERVER_ERROR("EMP_004");

    private final String code;

    ErrorCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}

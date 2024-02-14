package com.example.rqchallenge.exception;

import com.example.rqchallenge.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public APIException(ErrorCode errorCode, String message){
        this.errorCode = errorCode;
        this.message = message;
    }
}

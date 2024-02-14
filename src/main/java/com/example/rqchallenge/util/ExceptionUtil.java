package com.example.rqchallenge.util;

import com.example.rqchallenge.enums.ErrorCode;
import com.example.rqchallenge.exception.APIException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtil {
    public static APIException getAPIException(ErrorCode errorCode){
        String message = ErrorMessageUtil.getErrorMessage(errorCode);
        return new APIException(errorCode, message);
    }
}

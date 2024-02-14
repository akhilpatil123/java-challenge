package com.example.rqchallenge.util;

import com.example.rqchallenge.enums.ErrorCode;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ErrorMessageUtil {
    private static Map<ErrorCode,String> errorMessageMap = new HashMap<>();

    static {
        errorMessageMap.put(ErrorCode.EXCEPTION_WHILE_CALLING_EXTERNAL_API, "Exception while calling external API");
        errorMessageMap.put(ErrorCode.NO_RECORDS_FOUND, "No records found");
        errorMessageMap.put(ErrorCode.INVALID_INPUT, "Invalid input");
    }

    public static String getErrorMessage(ErrorCode errorCode) {
        return errorMessageMap.get(errorCode);
    }
}

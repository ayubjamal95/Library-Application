package com.librarymanagement.utils;

import com.librarymanagement.dto.ResultResponse;

public class ApiResponseUtils {
    public static void success(String message, ResultResponse response ) {
        response.setStatus(Constants.SUCCESS);
        response.setMessage(message);
        response.setCode(Constants.SUCCESS_CODE);
    }

    public static void failure(String message,ResultResponse response) {
        response.setStatus(Constants.FAILED);
        response.setMessage(message);
        response.setCode(Constants.FAILED_CODE);
    }
}

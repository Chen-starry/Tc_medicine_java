package com.tcmedicine.dto;

import lombok.Data;

/**
 * 通用API响应类
 */
@Data
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private Integer userType;

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }

    public static <T> ApiResponse<T> loginSuccess(Integer userType) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setUserType(userType);
        return response;
    }

} 
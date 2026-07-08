package com.example.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, "success", data);
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(200, "success", null);
    }

    public static <T> BaseResponse<T> error(int code, String msg) {
        return new BaseResponse<>(code, msg, null);
    }

    public static <T> BaseResponse<T> error(String msg) {
        return new BaseResponse<>(500, msg, null);
    }
}

package com.example.common.exception;

import com.example.common.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<Void> handleBusinessException(BusinessException e) {
        log.warn("Biz exception: code={}, msg={}", e.getCode(), e.getMessage());
        return BaseResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<Void> handleException(Exception e) {
        log.error("Unexpected exception", e);
        return BaseResponse.error("System error: " + e.getMessage());
    }
}

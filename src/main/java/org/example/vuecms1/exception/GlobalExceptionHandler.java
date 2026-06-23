package org.example.vuecms1.exception;

import org.example.vuecms1.exception.PermissionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import util.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PermissionException.class)
    public Result<Object> handlePermissionException(PermissionException e) {
        // 返回 403 状态码（Forbidden）或业务约定的错误码
        return Result.fail(403, e.getMessage());
    }
}
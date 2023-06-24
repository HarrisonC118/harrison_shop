package com.harrison.exception;

import com.harrison.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handler(Exception e) {
        // 是不是自定义异常
        if (e instanceof StatusCodeException) {
            log.error("业务异常:{}", e.getMessage());
            StatusCodeException statusCodeException = (StatusCodeException) e;
            return JsonData.fail(statusCodeException.getCode(), statusCodeException.getMsg());
        } else {
            log.error("非业务异常:{}", e.getMessage());
            return JsonData.fail("后端发生错误，错误信息：" + e.getMessage());
        }
    }
}

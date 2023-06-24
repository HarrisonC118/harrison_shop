package com.harrison.exception;

import com.harrison.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusCodeException extends RuntimeException {
    private Integer code;
    private String msg;

    public StatusCodeException(StatusCodeEnum e) {
        super(e.getCode() + ":" + e.getMsg());
        this.code = e.getCode();
        this.msg = e.getMsg();
    }
}

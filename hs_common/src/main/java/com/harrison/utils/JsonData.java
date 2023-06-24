package com.harrison.utils;

import com.harrison.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {
    private Integer code;
    private String msg;
    private Object data;

    public static JsonData success() {
        return new JsonData(200, "success", null);
    }

    public static JsonData success(Object data) {
        return new JsonData(200, "success", data);
    }

    public static JsonData success(String msg, Object data) {
        return new JsonData(200, msg, data);
    }

    public static JsonData success(String msg) {
        return new JsonData(200, msg, null);
    }

    public static JsonData fail() {
        return new JsonData(500, "fail", null);
    }

    public static JsonData fail(String msg) {
        return new JsonData(500, msg, null);
    }

    public static JsonData fail(String msg, Object data) {
        return new JsonData(500, msg, data);
    }

    public static JsonData fail(Integer code, String msg) {
        return new JsonData(code, msg, null);
    }

    public static JsonData fail(Integer code, String msg, Object data) {
        return new JsonData(code, msg, data);
    }

    public static JsonData definedResult(StatusCodeEnum statusCodeEnum) {
        return new JsonData(statusCodeEnum.getCode(), statusCodeEnum.getMsg(), null);
    }

}

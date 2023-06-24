package com.harrison.enums;

import lombok.Getter;

/**
 * 错误状态码枚举类
 */
@Getter
public enum StatusCodeEnum {
    /**
     * 通用
     */
    OPS_REPEAT(110001, "操作重复"),
    /**
     * 验证码
     */
    CODE_TO_ERROR(240001, "验证码错误"),
    CODE_LIMITED(240002, "验证码发送过快"),
    CODE_CAPTCHA_ERROR(240101, "图形验证码错误"),
    /**
     * 账号
     */
    ACCOUNT_REPEAT(250001, "账号重复"),
    ACCOUNT_NOT_EXIST(250002, "账号不存在"),
    ACCOUNT_PASSWORD_ERROR(250003, "账号或密码错误");

    private final String msg;
    private final Integer code;

    private StatusCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}

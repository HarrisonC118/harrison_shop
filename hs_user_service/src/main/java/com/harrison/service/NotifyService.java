package com.harrison.service;

import com.harrison.enums.SendCodeEnum;
import com.harrison.utils.JsonData;

public interface NotifyService {
    /**
     * 发送验证码
     *
     * @param sendCodeEnum 验证码类型 是注册验证码还是忘记密码验证码
     * @param to           接收人
     * @return
     */
    JsonData sendCode(SendCodeEnum sendCodeEnum, String to);
}

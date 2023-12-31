package com.harrison.service;

import com.harrison.enums.BusinessName;
import com.harrison.utils.JsonData;

public interface NotifyService {
    /**
     * 发送验证码
     *
     * @param businessName 验证码类型 是注册验证码还是忘记密码验证码
     * @param to           接收人
     * @return
     */
    JsonData sendCode(BusinessName businessName, String to);

    /**
     * 校验验证码
     *
     * @param businessName 验证码类型
     * @param to           接收人
     * @param code         验证码
     * @return 校验结果
     */
    Boolean verifyCode(BusinessName businessName, String to, String code);
}

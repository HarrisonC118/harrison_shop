package com.harrison.service.impl;

import com.harrison.enums.SendCodeEnum;
import com.harrison.service.MailService;
import com.harrison.service.NotifyService;
import com.harrison.utils.CommonUtil;
import com.harrison.utils.JsonData;
import com.harrison.utils.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private MailService mailService;

    private static final String SUBJECT = "HarrisonShop Verification Code";
    private static final String CONTENT = "您的验证码为：%s, 有效时间为5分钟，请勿泄露给他人使用。";

    @Override
    public JsonData sendCode(SendCodeEnum sendCodeEnum, String to) {
        // 判断是手机号还是邮箱
        if (VerifyUtil.isEmail(to)) {
            // 获取随机验证码
            String verificationCode = CommonUtil.getRandomCode(6);
            mailService.sendMail(to, SUBJECT, String.format(CONTENT, verificationCode));
            return JsonData.success("验证码发送成功");
        } else if (VerifyUtil.isPhone(to)) {
            //TODO: 发送短信验证码
        } else {
            return JsonData.fail("邮箱或者手机号格式不正确");
        }
        return null;
    }
}

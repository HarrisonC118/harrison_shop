package com.harrison.service.impl;

import com.harrison.constants.CacheNameTemplate;
import com.harrison.enums.BusinessName;
import com.harrison.enums.StatusCodeEnum;
import com.harrison.service.MailService;
import com.harrison.service.NotifyService;
import com.harrison.utils.CommonUtil;
import com.harrison.utils.JsonData;
import com.harrison.utils.VerifyUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private MailService mailService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String MAIL_SUBJECT = "HarrisonShop Verification Code";
    private static final String MAIL_CONTENT = "您的验证码为：%s, 有效时间为5分钟，请勿泄露给他人使用。";
    // 发送验证码间隔不能小于60秒
    private static final Integer Minimum_Time_Interval = 60 * 1000;
    // 验证码过期时间为5分钟
    private static final Integer CAPTCHA_EXPIRED_TIME = 60 * 5 * 1000;

    @Override
    public JsonData sendCode(BusinessName businessName, String to) {
        // 从邮箱角度判断间隔时间是否合法
        if (!checkIntervalTime(businessName.name(), to)) {
            return JsonData.fail(StatusCodeEnum.CODE_LIMITED);
        }
        // 从IP角度判断间隔时间是否合法
        // TODO: 从IP角度判断间隔时间是否合法

        // 生成邮箱或手机号验证码
        String verificationCode = CommonUtil.getRandomCode(6);
        // 判断是手机号还是邮箱
        if (VerifyUtil.isEmail(to)) {
            // 发送邮件
            mailService.sendMail(to, MAIL_SUBJECT, String.format(MAIL_CONTENT, verificationCode));
            // 发送邮件后，将验证码存入redis
            String cacheKey = String.format(CacheNameTemplate.CHECK_CODE_KEY, businessName.name(), to);
            redisTemplate.opsForValue().set(cacheKey, verificationCode + "_" + System.currentTimeMillis(), CAPTCHA_EXPIRED_TIME, TimeUnit.MILLISECONDS);
            return JsonData.success("验证码发送成功");
        } else if (VerifyUtil.isPhone(to)) {
            //TODO: 发送短信验证码
        } else {
            return JsonData.fail("邮箱或者手机号格式不正确");
        }
        return null;
    }

    /**
     * 检查发送时间间隔，如果小于60秒则不发送
     * 缓存命名格式是：服务名:业务名:发给谁
     * 内容是：验证码_时间戳
     * 这里主要是防止往同一个邮箱或者手机号在60秒内重复发送验证码
     *
     * @return 发送时间是否合理 true：合理 false：不合理
     */
    private boolean checkIntervalTime(String businessName, String to) {
        String cacheKey = String.format(CacheNameTemplate.CHECK_CODE_KEY, businessName, to);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotBlank(cacheValue)) {
            // 取出时间戳
            String[] split = cacheValue.split("_");
            long lastSendTime = Long.parseLong(split[1]);
            // 判断间隔时间是否小于60秒
            return System.currentTimeMillis() - lastSendTime >= Minimum_Time_Interval;
        }
        return true;
    }
}

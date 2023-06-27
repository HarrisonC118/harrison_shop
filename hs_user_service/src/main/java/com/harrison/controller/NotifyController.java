package com.harrison.controller;

import com.google.code.kaptcha.Producer;
import com.harrison.entity.bo.SendRegisterCodeParams;
import com.harrison.enums.SendCodeEnum;
import com.harrison.enums.StatusCodeEnum;
import com.harrison.service.NotifyService;
import com.harrison.utils.CommonUtil;
import com.harrison.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码接口
 */
@RestController
@RequestMapping("/api/notify/v1/")
@Slf4j
public class NotifyController {
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private Producer captchaProducer;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String CAPTCHA_PREFIX = "user-service:captcha:";

    /**
     * 验证码过期时间为3分钟
     */
    private static final Integer CAPTCHA_EXPIRED_TIME = 60 * 3000;

    /**
     * 生成验证码图片
     *
     * @param response 响应
     */
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        String text = captchaProducer.createText();
        // 将验证码存入redis
        String userUniqueIdentifierKeyMd5 = getUserUniqueIdentifierKey_MD5(request);
        redisTemplate.opsForValue().set(userUniqueIdentifierKeyMd5, text, CAPTCHA_EXPIRED_TIME, TimeUnit.MILLISECONDS);

        BufferedImage image = captchaProducer.createImage(text);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            log.error("验证码生成失败", e);
        }
    }

    @PostMapping("/sendRegisterCode")
    public JsonData sendRegisterCode(@RequestBody SendRegisterCodeParams params, HttpServletRequest request) {
        // 校验验证码是否正确
        String userUniqueIdentifierKeyMd5 = getUserUniqueIdentifierKey_MD5(request);
        String correctCaptcha = redisTemplate.opsForValue().get(userUniqueIdentifierKeyMd5);
        if (StringUtils.isNotBlank(correctCaptcha) && StringUtils.equalsIgnoreCase(correctCaptcha, params.getCaptcha())) {
            // 使验证码失效
            redisTemplate.delete(userUniqueIdentifierKeyMd5);
            log.info("验证码校验成功");
            // 发送邮件
            return notifyService.sendCode(SendCodeEnum.USER_REGISTER, params.getTo());
        } else {
            log.info("验证码校验失败");
            return JsonData.fail(StatusCodeEnum.CODE_ERROR);
        }
    }

    /**
     * 获取用户唯一标识，以MD5加密
     *
     * @param request 请求
     * @return 用户唯一标识
     */
    private String getUserUniqueIdentifierKey_MD5(HttpServletRequest request) {
        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        String key = CAPTCHA_PREFIX + DigestUtils.md5DigestAsHex((ip + userAgent).getBytes());
        log.info("key:{}", key);
        log.info("ip:{}", ip);
        log.info("userAgent:{}", userAgent);
        return key;
    }
}

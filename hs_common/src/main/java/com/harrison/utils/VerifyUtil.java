package com.harrison.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * 验证工具类
 */
public class VerifyUtil {
    private static final Pattern MAIL_PATTERN = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");

    private static final Pattern PHONE_PATTERN = Pattern.compile("/^(\\+?0{0,2}86([\\ |\\-])?)?1[3|4|5|7|8][0-9]{9}$/");

    /**
     * 验证邮箱格式是否正确
     *
     * @param email 邮箱
     * @return 验证结果
     */
    public static boolean isEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return false;
        }
        return MAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 验证手机号码格式是否正确
     *
     * @param phone 手机号码
     * @return 验证结果
     */
    public static boolean isPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }
}

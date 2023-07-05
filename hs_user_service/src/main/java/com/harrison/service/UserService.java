package com.harrison.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.harrison.entity.User;
import com.harrison.entity.bo.UserLoginParams;
import com.harrison.enums.BusinessName;
import com.harrison.utils.JsonData;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author harrison
 * @since 2023-06-23
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param user         用户信息
     * @param businessName 验证码类型
     * @param code         验证码
     * @return 注册结果
     */
    JsonData register(User user, BusinessName businessName, String code);

    /**
     * 用户登录
     *
     * @param user 用户信息
     * @return 登录结果
     */
    JsonData login(UserLoginParams user);

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    JsonData getUserDetail();
}

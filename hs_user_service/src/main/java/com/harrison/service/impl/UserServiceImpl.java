package com.harrison.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harrison.entity.User;
import com.harrison.entity.VO.UserDetailVO;
import com.harrison.entity.bo.UserLoginParams;
import com.harrison.enums.BusinessName;
import com.harrison.enums.StatusCodeEnum;
import com.harrison.interceptor.LoginInterceptor;
import com.harrison.mapper.UserMapper;
import com.harrison.model.LoginUser;
import com.harrison.service.NotifyService;
import com.harrison.service.UserService;
import com.harrison.utils.CommonUtil;
import com.harrison.utils.JsonData;
import com.harrison.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author harrison
 * @since 2023-06-23
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * * 邮箱验证码校验
     * * 密码加密
     * * 保存用户信息
     * * 生成token
     * * 返回token
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public JsonData register(User user, BusinessName businessName, String code) {
        if (StringUtils.isNotBlank(user.getMail())) {
            // 校验验证码
            Boolean verifyCodeResult = notifyService.verifyCode(businessName, user.getMail(), code);
            if (!verifyCodeResult) {
                // 验证码错误
                return JsonData.fail(StatusCodeEnum.CODE_ERROR);
            }
            // 校验邮箱是否已注册
            QueryWrapper<User> checkEmailWrapper = new QueryWrapper<>();
            checkEmailWrapper.eq("mail", user.getMail());
            User userFromDb = userMapper.selectOne(checkEmailWrapper);
            if (userFromDb != null) {
                // 邮箱已注册
                return JsonData.fail(StatusCodeEnum.EMAIL_REGISTERED);
            }
            // 用户信息前置处理
            User preHandleUser = preHandleUser(user);
            int insert = userMapper.insert(preHandleUser);
            if (insert > 0) {
                // 注册成功
                log.info("用户注册成功，用户信息：{}", preHandleUser);
                // 处理新用户注册后的逻辑
                userRegisterInitTask(preHandleUser);
                return JsonData.success();
            }
        }

        return JsonData.fail(StatusCodeEnum.REGISTER_ERROR);
    }

    @Override
    public JsonData login(UserLoginParams user) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("mail", user.getMail());
        List<User> users = userMapper.selectList(userQueryWrapper);
        for (User currentUser : users) {
            String salt = currentUser.getSalt();
            String password = currentUser.getPassword();
            String md5Crypt = Md5Crypt.md5Crypt(user.getPassword().getBytes(), salt);
            if (md5Crypt.equals(password)) {
                // 登录成功
                log.info("用户登录成功，用户信息：{}", currentUser);
                // 生成token
                LoginUser loginUser = new LoginUser(currentUser.getId(), currentUser.getNickname(), currentUser.getHeadPortrait(), currentUser.getMail());
                String s = JwtUtil.generateJwt(loginUser);
                HashMap<String, String> map = new HashMap<>();
                map.put("token", s);
                return JsonData.success("登录成功", map);
            }
        }
        return JsonData.fail(StatusCodeEnum.ACCOUNT_PASSWORD_ERROR);
    }

    @Override
    public JsonData getUserDetail() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id", loginUser.getId());
        User user = userMapper.selectOne(userQueryWrapper);
        UserDetailVO userDetailVO = new UserDetailVO();
        BeanUtils.copyProperties(user, userDetailVO);
        return JsonData.success(userDetailVO);
    }

    /**
     * 用户信息前置处理
     * * 密码加密
     * * 生成token
     *
     * @param user 用户信息
     * @return 处理后的用户信息
     */
    private User preHandleUser(User user) {
        // 密码加密
        String salt = "$1$" + CommonUtil.generateUUID(8);
        String md5Crypt = Md5Crypt.md5Crypt(user.getPassword().getBytes(), salt);
        log.info("用户密码加密后：{}，盐为：{}", md5Crypt, salt);
        user.setSalt(salt);
        user.setPassword(md5Crypt);
        return user;
    }


    /**
     * 新用户注册后的逻辑 TODO
     * 发放福利
     *
     * @param user 用户信息
     */
    private void userRegisterInitTask(User user) {
        // 新用户注册后的逻辑
    }
}

package com.harrison.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harrison.entity.User;
import com.harrison.mapper.UserMapper;
import com.harrison.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author harrison
 * @since 2023-06-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

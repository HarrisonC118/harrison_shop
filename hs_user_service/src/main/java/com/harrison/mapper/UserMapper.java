package com.harrison.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.harrison.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author harrison
 * @since 2023-06-23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

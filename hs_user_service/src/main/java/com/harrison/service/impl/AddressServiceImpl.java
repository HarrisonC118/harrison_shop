package com.harrison.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.harrison.entity.Address;
import com.harrison.mapper.AddressMapper;
import com.harrison.service.AddressService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收货地址表 服务实现类
 * </p>
 *
 * @author harrison
 * @since 2023-06-23
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

}

package com.harrison.controller;

import com.harrison.entity.Address;
import com.harrison.enums.StatusCodeEnum;
import com.harrison.exception.StatusCodeException;
import com.harrison.service.AddressService;
import com.harrison.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 收货地址表 前端控制器
 * </p>
 *
 * @author harrison
 * @since 2023-06-23
 */
@RestController
@RequestMapping("/api/address/v1/")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public Object addAddress(@RequestBody Address address) {
        boolean b = addressService.saveOrUpdate(address);
        return b ? JsonData.success() : JsonData.fail();
    }

    @GetMapping("/getAddressById/{id}")
    public Object listAddress(@PathVariable String id) {
        Address address = addressService.query().eq("id", id).one();
        throw new StatusCodeException(StatusCodeEnum.CODE_TO_ERROR);
//        return JsonData.success(address);
    }
}

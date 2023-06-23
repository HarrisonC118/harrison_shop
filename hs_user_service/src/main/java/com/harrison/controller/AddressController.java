package com.harrison.controller;

import com.harrison.entity.Address;
import com.harrison.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 收货地址表 前端控制器
 * </p>
 *
 * @author harrison
 * @since 2023-06-23
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public String addAddress(@RequestBody Address address) {
        boolean b = addressService.saveOrUpdate(address);
        return b ? "success" : "fail";
    }
}

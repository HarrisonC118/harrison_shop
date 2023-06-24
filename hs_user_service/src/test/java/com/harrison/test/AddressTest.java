package com.harrison.test;

import com.harrison.entity.Address;
import com.harrison.service.AddressService;
import junit.framework.Assert;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.harrison.UserApplication.class)
@Slf4j
public class AddressTest {
    @Autowired
    private AddressService addressService;

    @Test
    public void test() {
        Address byId = addressService.getById("1672233830678835201");
        log.info(byId.toString());
        Assert.assertNotNull(byId);
    }
}

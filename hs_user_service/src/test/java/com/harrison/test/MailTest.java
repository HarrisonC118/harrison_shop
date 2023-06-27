package com.harrison.test;

import com.harrison.UserApplication;
import com.harrison.service.MailService;
import com.harrison.utils.VerifyUtil;
import junit.framework.Assert;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UserApplication.class)
@Slf4j
public class MailTest {
    @Autowired
    private MailService mailService;

    @Test
    public void sendMailTest() {
        mailService.sendMail("2324782992@qq.com", "测试邮件", "测试邮件");
    }

    @Test
    public void verifyEmailTest() {
        boolean email1 = VerifyUtil.isEmail("123123@qq.com");
        boolean email2 = VerifyUtil.isEmail("1f51ew5a@5f");
        boolean email3 = VerifyUtil.isEmail("1f51ew5a.cn");
        Assert.assertTrue(email1);
        Assert.assertFalse(email2);
        Assert.assertFalse(email3);
    }
}

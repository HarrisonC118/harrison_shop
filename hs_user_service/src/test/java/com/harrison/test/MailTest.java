package com.harrison.test;

import com.harrison.UserApplication;
import com.harrison.service.MailService;
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
}

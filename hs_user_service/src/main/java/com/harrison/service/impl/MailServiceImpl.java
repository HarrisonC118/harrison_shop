package com.harrison.service.impl;

import com.harrison.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private MailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void sendMail(String to, String subject, String content) {
        // 创建邮箱消息
        SimpleMailMessage message = new SimpleMailMessage();
        // 创建邮箱发送人
        message.setFrom(from);
        // 创建收件人
        message.setTo(to);
        // 创建邮件主题
        message.setSubject(subject);
        // 创建邮件内容
        message.setText(content);
        // 发送邮件
        mailSender.send(message);
        log.info("邮件成功发给了{}", to);
    }
}

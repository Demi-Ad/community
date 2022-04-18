package com.example.community.common.mailing.service;

import com.example.community.common.mailing.dto.MailDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {
    private final JavaMailSender emailSender;
    private final String fromAddress;

    @Autowired
    public MailService(JavaMailSender emailSender, @Value("${spring.mail.username}") String fromAddress) {
        this.fromAddress = fromAddress;
        this.emailSender = emailSender;
    }

    @Async
    public void sendMail(MailDto mailDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(this.fromAddress);
        simpleMailMessage.setTo(mailDto.getMailAddress());
        simpleMailMessage.setSubject(mailDto.getTitle());
        simpleMailMessage.setText(mailDto.getMessage());
        emailSender.send(simpleMailMessage);
    }
}

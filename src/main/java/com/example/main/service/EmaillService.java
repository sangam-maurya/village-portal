package com.example.main.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmaillService {

    private final JavaMailSender javaMailSender;

    public EmaillService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMsg(String to , String subject , String text){
        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("sangamm161@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

}

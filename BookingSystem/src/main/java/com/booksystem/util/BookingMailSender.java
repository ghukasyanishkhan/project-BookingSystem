package com.booksystem.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class BookingMailSender {
    @Autowired
    private MailSender mailSender;
    public void sendEmail(String to,String subject, String text){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("bookingsystembook@yandex.ru");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}

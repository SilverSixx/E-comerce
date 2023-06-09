package com.example.ecommerce.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender{
    private final JavaMailSender mailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    @Override
    @Async // not block the client
    public void send(String to, String email) {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper  = new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirmed your email");
            helper.setFrom("myproject@mail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e){
            LOGGER.error("fail to send email", e);
            throw new IllegalStateException("fail to send email");
        }
    }
}

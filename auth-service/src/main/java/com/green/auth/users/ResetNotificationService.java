package com.green.auth.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ResetNotificationService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${app.security.reset.mail-from:no-reply@example.com}")
    private String mailFrom;

    public void sendEmailToken(String toEmail, String token) {
        if (mailSender == null || toEmail == null || toEmail.isBlank()) {
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailFrom);
            message.setTo(toEmail);
            message.setSubject("Password reset");
            message.setText("Use this token to reset your password: " + token);
            mailSender.send(message);
        } catch (Exception ignored) {
        }
    }

    public void sendSmsToken(String phone, String token) {
        // placeholder for SMS provider integration
    }
}

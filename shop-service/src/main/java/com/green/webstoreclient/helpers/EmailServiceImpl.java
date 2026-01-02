package com.green.webstoreclient.helpers;

import java.util.Locale;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailServiceImpl {

    
  
	public static void sendSimpleMessage(JavaMailSender emailSender, String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("thanhcong4135@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	public static void sendHTMLEmail(JavaMailSender mailSender, String from, String to, String subject,
			String message) {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

		try {
			helper.setSubject(subject);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText("<h1>KARL FASION STORE</h1><BR>" + message);

			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
    public static void sendSimpleMail( JavaMailSender mailSender,
            final String recipientName, final String recipientEmail, final Locale locale) {
        try {
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject("Example HTML email (simple)");
            message.setFrom("no-reply@example.com");
            message.setTo(recipientEmail);

            final String htmlContent = "<h1>Hello " + recipientName + "</h1><p>Welcome to our store.</p>";
            message.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

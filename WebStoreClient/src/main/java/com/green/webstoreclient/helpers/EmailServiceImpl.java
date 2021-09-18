package com.green.webstoreclient.helpers;

import java.util.Date;
import java.util.Locale;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class EmailServiceImpl {
	
	private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "email/email-simple";
	

    
  
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
	
    public static void sendSimpleMail( JavaMailSender mailSender, TemplateEngine htmlTemplateEngine,
            final String recipientName, final String recipientEmail, final Locale locale)
            throws MessagingException {

            // Prepare the evaluation context
            final Context ctx = new Context(locale);
            ctx.setVariable("name", recipientName);
            ctx.setVariable("subscriptionDate", new Date());
            ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

            // Prepare message using a Spring helper
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject("Example HTML email (simple)");
            message.setFrom("thymeleaf@example.com");
            message.setTo(recipientEmail);

            // Create the HTML body using Thymeleaf
            final String htmlContent = htmlTemplateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
            message.setText(htmlContent, true /* isHtml */);

            // Send email
           mailSender.send(mimeMessage);
        }
}

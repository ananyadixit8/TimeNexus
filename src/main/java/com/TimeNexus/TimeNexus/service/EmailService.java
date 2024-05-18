package com.TimeNexus.TimeNexus.service;

import com.TimeNexus.TimeNexus.model.MeetingInfo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Value("${spring.mail.from}")
    private String fromEmail;

    public void sendSimpleEmail(String to, MeetingInfo meetingInfo) throws MessagingException {

        // Create a Thymeleaf context and add your object as a variable
        Context context = new Context();
        context.setVariable("meetingInfo", meetingInfo);

        String subject = "[TimeNexus Meetings] [Reminder] Your meeting " + meetingInfo.getSubject() + " starts soon";

        // Process the Thymeleaf template
        String emailBody = templateEngine.process("emailTemplate", context);

        // Create a MimeMessage
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject("[TimeNexus Meetings] [Reminder] Your meeting " + meetingInfo.getSubject() + " starts soon");
        helper.setText(emailBody, true); // 'true' indicates the email body is HTML

        // Send the email
        javaMailSender.send(message);


    }

}

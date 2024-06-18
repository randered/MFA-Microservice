package com.randered.mfa.service;

import com.randered.mfa.config.MailConfig;
import com.randered.mfa.entity.MfaEntity;
import com.randered.mfa.exception.EmailSendingException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Value("${spring.mail.username}")
    private String email;
    @Autowired
    private MailConfig mailConfig;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(final MfaEntity mfaEntity) throws EmailSendingException {
        try {
            Message message = new MimeMessage(mailConfig.getGoogleSession());
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mfaEntity.getEmail()));
            message.setSubject("Your MFA Code");
            message.setText("Your MFA code is: " + mfaEntity.getCode());
            Transport.send(message);
            log.info("MFA code to {} send successfully.", mfaEntity.getEmail());

        } catch (MessagingException e) {
            log.error("Failed to send MFA code to {}", mfaEntity.getEmail(), e);
            throw new EmailSendingException("Failed to send email to " + mfaEntity.getEmail(), e);
        }
    }
}

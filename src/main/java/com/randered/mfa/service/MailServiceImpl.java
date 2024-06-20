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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.randered.mfa.constants.Constants.EMAIL_BODY;
import static com.randered.mfa.constants.Constants.EMAIL_SUBJECT;
import static com.randered.mfa.constants.Constants.LOG_FAILURE;
import static com.randered.mfa.constants.Constants.LOG_FAILURE_EXCEPTION;
import static com.randered.mfa.constants.Constants.LOG_SUCCESS;

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

    @Async
    public void sendEmail(final MfaEntity mfaEntity) throws EmailSendingException {
        try {
            Message message = new MimeMessage(mailConfig.getGmailSession());
            message.setFrom(new InternetAddress(email));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mfaEntity.getEmail()));
            message.setSubject(EMAIL_SUBJECT);
            message.setText(EMAIL_BODY + mfaEntity.getCode());
            Transport.send(message);
            log.info(LOG_SUCCESS, mfaEntity.getEmail());

        } catch (MessagingException e) {
            log.error(LOG_FAILURE, mfaEntity.getEmail(), e);
            throw new EmailSendingException(LOG_FAILURE_EXCEPTION + mfaEntity.getEmail(), e);
        }
    }
}

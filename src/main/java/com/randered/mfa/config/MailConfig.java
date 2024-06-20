package com.randered.mfa.config;

import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttlsEnable;

    public Session getGmailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        props.put("mail.smtp.auth", String.valueOf(smtpAuth));
        props.put("mail.smtp.starttls.enable", String.valueOf(starttlsEnable));

        return Session.getInstance(props, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}

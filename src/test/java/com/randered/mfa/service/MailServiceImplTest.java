package com.randered.mfa.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.randered.mfa.base.BaseTest;
import com.randered.mfa.entity.MfaEntity;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class MailServiceImplTest extends BaseTest {

    private GreenMail greenMail;

    @Autowired
    private MailServiceImpl mailService;

    @BeforeEach
    public void setUp() {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.start();
    }

    @AfterEach
    public void tearDown() {
        greenMail.stop();
    }

    @Test
    public void testSendEmailSuccess() throws Exception {
        MfaEntity mfaEntity = new MfaEntity();
        mfaEntity.setEmail("test@localhost.com");
        mfaEntity.setCode(UUID.randomUUID());

        mailService.sendEmail(mfaEntity);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertThat(receivedMessages).hasSize(1);
        MimeMessage receivedMessage = receivedMessages[0];
        assertThat(receivedMessage.getSubject()).isEqualTo("Your MFA Code");
        assertThat(GreenMailUtil.getBody(receivedMessage)).contains("Your MFA code is: ");
    }
}
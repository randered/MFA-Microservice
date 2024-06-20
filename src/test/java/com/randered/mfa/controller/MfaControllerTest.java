package com.randered.mfa.controller;

import com.randered.mfa.entity.MfaEntity;
import com.randered.mfa.exception.EmailSendingException;
import com.randered.mfa.service.CodeService;
import com.randered.mfa.service.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static com.randered.mfa.constants.Constants.EMAIL_FAILED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MfaController.class)
class MfaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CodeService codeService;

    @MockBean
    private MailService mailService;

    @Test
    public void testSendCodeSuccess() throws Exception {
        String email = "test@example.com";
        MfaEntity mfaEntity = new MfaEntity();
        mfaEntity.setEmail(email);

        when(codeService.issueMfaCode(email)).thenReturn(mfaEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/mfa/send")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"message":"Email successfully send to: test@example.com","requestType":"ISSUE_CODE"}"""));
    }

    @Test
    public void testSendCodeFailure() throws Exception {
        String email = "test@example.com";
        MfaEntity mfaEntity = new MfaEntity();
        mfaEntity.setEmail(email);

        when(codeService.issueMfaCode(email)).thenReturn(mfaEntity);
        doThrow(new EmailSendingException(EMAIL_FAILED)).when(mailService).sendEmail(any(MfaEntity.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/mfa/send")
                        .param("email", email)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"message":"Email delivery failed for: test@example.com","requestType":"ISSUE_CODE"}"""));
    }

    @Test
    public void testVerifyCodeSuccess() throws Exception {
        String email = "test@example.com";
        UUID code = UUID.randomUUID();
        when(codeService.verifyMfaCode(email, code)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/mfa/verify")
                        .param("email", email)
                        .param("code", code.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"message":"Code was successfully validated!","requestType":"VERIFY_CODE"}"""));
    }

    @Test
    public void testVerifyCodeFailure() throws Exception {
        String email = "test@example.com";
        UUID code = UUID.randomUUID();
        when(codeService.verifyMfaCode(email, code)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/mfa/verify")
                        .param("email", email)
                        .param("code", code.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"message":"Code has failed the validation process!","requestType":"VERIFY_CODE"}"""));
    }
}
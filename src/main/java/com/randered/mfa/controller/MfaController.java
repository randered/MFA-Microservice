package com.randered.mfa.controller;

import com.randered.mfa.entity.MfaEntity;
import com.randered.mfa.exception.EmailSendingException;
import com.randered.mfa.model.MfaResponse;
import com.randered.mfa.service.CodeService;
import com.randered.mfa.service.MailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.randered.mfa.constants.Constants.CODE_FAILED_VALIDATION;
import static com.randered.mfa.constants.Constants.CODE_SUCCESSFULLY_VALIDATED;
import static com.randered.mfa.constants.Constants.EMAIL_FAILED;
import static com.randered.mfa.constants.Constants.EMAIL_SUCCESSFULLY_SEND;

@RestController
@RequestMapping("/api/mfa")
public class MfaController {

    @Autowired
    private CodeService codeService;
    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public MfaResponse sendMfaCode(@Valid final String email) {
        try {
            final MfaEntity issued = codeService.issueMfaCode(email);
            mailService.sendEmail(issued);
            codeService.update(issued);
            return MfaResponse.builder().message(EMAIL_SUCCESSFULLY_SEND + email).build();
        } catch (EmailSendingException e) {
            return MfaResponse.builder().message(EMAIL_FAILED + email).build();
        }
    }

    @PostMapping("/verify")
    public MfaResponse verifyMfaCode(@Valid final String email, @RequestParam final UUID code) {
        if (codeService.verifyMfaCode(email, code)) {
            return MfaResponse.builder().message(CODE_SUCCESSFULLY_VALIDATED).build();
        }
        return MfaResponse.builder().message(CODE_FAILED_VALIDATION).build();
    }
}

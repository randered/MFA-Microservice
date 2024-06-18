package com.randered.mfa.service;

import com.randered.mfa.entity.MfaEntity;
import com.randered.mfa.exception.EmailSendingException;

public interface MailService {

    public void sendEmail(final MfaEntity mfaEntity) throws EmailSendingException;
}

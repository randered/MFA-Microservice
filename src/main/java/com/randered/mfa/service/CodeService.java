package com.randered.mfa.service;

import com.randered.mfa.entity.MfaEntity;

import java.util.UUID;

public interface CodeService {

    public MfaEntity update(final MfaEntity mfaEntity);

    MfaEntity issueMfaCode(final String email);

    boolean verifyMfaCode(final String email, final UUID code);
}

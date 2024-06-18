package com.randered.mfa.service;

import com.randered.mfa.entity.MfaEntity;
import com.randered.mfa.repository.MfaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.randered.mfa.constants.Constants.TIME_DURATION;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    private MfaRepository mfaRepository;

    @Override
    public MfaEntity issueMfaCode(final String email) {
        MfaEntity found = mfaRepository.findByEmail(email);
        final UUID code = generateUniqueCode();

        if (found == null) {
            return mfaEntity(null, email, code);
        } else {
            return mfaEntity(found, email, code);
        }
    }

    @Override
    public boolean verifyMfaCode(final String email, final UUID code) {
        final MfaEntity mfaEntity = mfaRepository.findByEmail(email);
        return mfaEntity != null && code.equals(mfaEntity.getCode()) && isCodeValid(mfaEntity);
    }

    private MfaEntity mfaEntity(final MfaEntity mfaEntity, final String email, final UUID code) {
        if (mfaEntity == null) {
            return MfaEntity.builder()
                    .email(email)
                    .code(code)
                    .expiryTime(setExpiryTime())
                    .build();
        } else {
            mfaEntity.setCode(code);
            mfaEntity.setExpiryTime(setExpiryTime());
            return mfaEntity;
        }
    }

    public MfaEntity update(final MfaEntity mfaEntity){
        return mfaRepository.save(mfaEntity);
    }

    private boolean isCodeValid(final MfaEntity mfaEntity) {
        return mfaEntity.getExpiryTime() != null && mfaEntity.getExpiryTime().isAfter(LocalDateTime.now());
    }

    private UUID generateUniqueCode() {
        return UUID.randomUUID();
    }

    private LocalDateTime setExpiryTime() {
        return LocalDateTime.now().plusMinutes(TIME_DURATION);
    }
}

package com.randered.mfa.repository;

import com.randered.mfa.base.BaseTest;
import com.randered.mfa.entity.MfaEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class MfaRepositoryTest extends BaseTest {

    @Autowired
    private MfaRepository mfaRepository;

    private MfaEntity mfaEntity;
    private UUID code;

    @BeforeEach
    public void setUp() {
        mfaEntity = new MfaEntity();
        code = UUID.randomUUID();
        mfaEntity.setEmail("test@example.com");
        mfaEntity.setCode(code);
        mfaRepository.save(mfaEntity);
    }

    @Test
    public void testFindByEmail() {
        MfaEntity foundEntity = mfaRepository.findByEmail("test@example.com");
        assertThat(foundEntity).isNotNull();
        assertThat(foundEntity.getEmail()).isEqualTo("test@example.com");
        assertThat(foundEntity.getCode()).isEqualTo(code);
    }

    @Test
    public void testFindByEmail_NotFound() {
        MfaEntity foundEntity = mfaRepository.findByEmail("nonexistent@example.com");
        assertThat(foundEntity).isNull();
    }
}
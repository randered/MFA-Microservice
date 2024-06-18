package com.randered.mfa.repository;

import com.randered.mfa.entity.MfaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MfaRepository extends JpaRepository<MfaEntity, Long> {
    MfaEntity findByEmail(final String email);
}

package com.randered.mfa.model;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MfaRequest {

    @Email
    private String email;
}

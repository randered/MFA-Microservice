package com.randered.mfa.model;

import com.randered.mfa.enums.RequestType;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MfaRequest {

    @Email
    private String email;

    private RequestType type;
}

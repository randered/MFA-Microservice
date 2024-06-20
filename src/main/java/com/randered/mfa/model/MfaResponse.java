package com.randered.mfa.model;

import com.randered.mfa.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MfaResponse {

    private String message;

    private RequestType requestType;
}

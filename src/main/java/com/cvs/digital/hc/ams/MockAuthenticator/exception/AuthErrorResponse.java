package com.cvs.digital.hc.ams.MockAuthenticator.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthErrorResponse(String errorMessage, int rawStatus) {

}

package com.cvs.digital.hc.ams.MockAuthenticator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;


@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthResponse(@JsonProperty("user_id") String id, String username, String email) {
}

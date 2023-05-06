package com.cvs.digital.hc.ams.MockAuthenticator.model;

import lombok.Builder;

@Builder
public record AuthRequest(String dataSource, String username, String password) {
}

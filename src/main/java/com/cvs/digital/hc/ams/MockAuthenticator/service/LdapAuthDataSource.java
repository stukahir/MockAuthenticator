package com.cvs.digital.hc.ams.MockAuthenticator.service;

import com.cvs.digital.hc.ams.MockAuthenticator.factory.AuthDataSource;
import com.cvs.digital.hc.ams.MockAuthenticator.model.User;
import org.springframework.stereotype.Service;

@Service
public class LdapAuthDataSource implements AuthDataSource {
    @Override
    public User authenticate(String username, String password) {
        return null;
    }
}

package com.cvs.digital.hc.ams.MockAuthenticator.factory;

import com.cvs.digital.hc.ams.MockAuthenticator.model.User;

public interface AuthDataSource {

    User authenticate(String username, String password);

}

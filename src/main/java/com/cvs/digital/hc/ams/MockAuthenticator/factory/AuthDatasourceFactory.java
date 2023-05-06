package com.cvs.digital.hc.ams.MockAuthenticator.factory;

import com.cvs.digital.hc.ams.MockAuthenticator.service.DatabaseAuthDataSource;
import com.cvs.digital.hc.ams.MockAuthenticator.service.LdapAuthDataSource;
import com.cvs.digital.hc.ams.MockAuthenticator.service.MockServiceDataSource;
import org.springframework.stereotype.Component;

@Component
public class AuthDatasourceFactory {

    public AuthDataSource getAuthDataSource(String dataSourceType) {
        return switch(dataSourceType) {
            case "ldap" -> new LdapAuthDataSource();
            case "database" -> new DatabaseAuthDataSource();
            case "mock"-> new MockServiceDataSource();
            default -> throw new IllegalArgumentException("Unsupported data source type: " + dataSourceType);
        };
    }

}

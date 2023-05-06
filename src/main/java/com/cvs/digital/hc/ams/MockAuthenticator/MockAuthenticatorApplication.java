package com.cvs.digital.hc.ams.MockAuthenticator;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "DB Mock Service", version = "1.0"), security = @SecurityRequirement(name =
        "Authorization"))
public class MockAuthenticatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockAuthenticatorApplication.class, args);
    }

}

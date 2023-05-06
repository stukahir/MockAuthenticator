package com.cvs.digital.hc.ams.MockAuthenticator.controller;

import com.cvs.digital.hc.ams.MockAuthenticator.exception.AuthErrorResponse;
import com.cvs.digital.hc.ams.MockAuthenticator.exception.AuthFailureException;
import com.cvs.digital.hc.ams.MockAuthenticator.factory.AuthDataSource;
import com.cvs.digital.hc.ams.MockAuthenticator.factory.AuthDatasourceFactory;
import com.cvs.digital.hc.ams.MockAuthenticator.model.AuthRequest;
import com.cvs.digital.hc.ams.MockAuthenticator.model.AuthResponse;
import com.cvs.digital.hc.ams.MockAuthenticator.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Auth Mocker Service", description = "mock service for the db abstraction layer")
public class AuthController {

    private final AuthDatasourceFactory authDatasourceFactory;
    private final String AUTH_ERROR_MSG = "Wrong Username Or Password Error";

    @Autowired
    public AuthController(AuthDatasourceFactory authDatasourceFactory) {
        this.authDatasourceFactory = authDatasourceFactory;
    }

    @Operation(summary = "Authenticate user", description = "Authenticate a user using the specified credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful authentication",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = AuthErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = AuthErrorResponse.class)))
    })
    @PostMapping("/v1/authenticate")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest request) throws AuthFailureException {
        log.info("authenticateUser started");
        try {
            AuthDataSource authDataSource = authDatasourceFactory.getAuthDataSource(request.dataSource());
            User user = authDataSource.authenticate(request.username(), request.password());
            return ResponseEntity.status(HttpStatus.OK).body(AuthResponse.builder()
                    .id(user.id())
                    .username(user.username())
                    .email(user.email())
                    .build());
        } catch (UsernameNotFoundException | IllegalArgumentException | NullPointerException ex) {
            log.error("Error Authenticating User: {}", request.username(), ex);
            throw new AuthFailureException(AUTH_ERROR_MSG, ex);
        }
    }


}


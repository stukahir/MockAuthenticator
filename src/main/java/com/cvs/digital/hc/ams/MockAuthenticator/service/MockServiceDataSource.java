package com.cvs.digital.hc.ams.MockAuthenticator.service;

import com.cvs.digital.hc.ams.MockAuthenticator.factory.AuthDataSource;
import com.cvs.digital.hc.ams.MockAuthenticator.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MockServiceDataSource implements AuthDataSource {
    private final List<Map<String, String>> mockUsers = List.of(
            Map.of("id", "001", "email", "user1@example.com", "username", "user1", "password", "password1"),
            Map.of("id", "002", "email", "user2@example.com", "username", "user2", "password", "password2"),
            Map.of("id", "003", "email", "user3@example.com", "username", "user3", "password", "password3")
    );

    @Override
    public User authenticate(String username, String password) {

        Map<String, String> mockUser = mockUsers.stream()
                .filter(u -> u.get("username").equals(username) && u.get("password").equals(password))
                .findFirst()
                .orElse(null);
        assert mockUser != null;
        return new User(mockUser.get("id"), mockUser.get("username"), mockUser.get("email"));
    }
}

package com.cvs.digital.hc.ams.MockAuthenticator.config;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebSecurityConfigurer<WebSecurity> {
    private final String[] WHITE_LIST = new String[]{"/swagger-ui/**", "/v3/api-docs/**", "/v1/**", "actuator/**", "/api", "/**"};

    @Override
    public void init(WebSecurity webSecurity) {
        webSecurity.ignoring().requestMatchers(WHITE_LIST).and()
                .debug(true)
                .addSecurityFilterChainBuilder(() -> new SecurityFilterChain() {
                    @Override
                    public boolean matches(HttpServletRequest request) {
                        return true;
                    }

                    @Override
                    public List<Filter> getFilters() {
                        return new ArrayList<>();
                    }
                });


    }

    @Override
    public void configure(WebSecurity builder) {
        builder.ignoring()
                .requestMatchers(WHITE_LIST).and()
                .debug(true); // false
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(EndpointRequest.to(WHITE_LIST)).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.csrf().ignoringRequestMatchers(WHITE_LIST);
        http.headers().frameOptions().sameOrigin();
        http.headers().frameOptions().disable();

        return http.build();
    }


}


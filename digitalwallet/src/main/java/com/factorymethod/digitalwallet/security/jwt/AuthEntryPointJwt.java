package com.factorymethod.digitalwallet.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        if (authException instanceof BadCredentialsException) {
            log.error("Bad credentials: {}", authException.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error: Bad credentials");
        } else if (authException instanceof LockedException) {
            log.error("User account is locked: {}", authException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: User account is locked");
        } else if (authException instanceof DisabledException) {
            log.error("User account is disabled: {}", authException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: User account is disabled");
        } else if (authException instanceof AccountExpiredException) {
            log.error("User account has expired: {}", authException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: User account has expired");
        } else if (authException instanceof CredentialsExpiredException) {
            log.error("User credentials have expired: {}", authException.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: User credentials have expired");
        }
    }
}
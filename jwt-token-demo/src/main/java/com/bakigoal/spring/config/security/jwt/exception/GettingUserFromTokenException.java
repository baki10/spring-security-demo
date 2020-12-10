package com.bakigoal.spring.config.security.jwt.exception;

import org.springframework.security.core.AuthenticationException;

public class GettingUserFromTokenException extends AuthenticationException {

    public GettingUserFromTokenException(String msg) {
        super(msg);
    }
}

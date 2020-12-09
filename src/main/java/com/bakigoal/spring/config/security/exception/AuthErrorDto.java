package com.bakigoal.spring.config.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthErrorDto {

    private final String message;
}

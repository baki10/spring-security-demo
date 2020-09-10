package com.bakigoal.spring.exception;

public class AccessCheckException extends RuntimeException {
    public AccessCheckException(String message) {
        super(message);
    }
}

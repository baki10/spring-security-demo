package com.bakigoal.spring.security.interceptor;

import com.bakigoal.spring.config.security.common.MyUserDetails;
import com.bakigoal.spring.exception.AccessCheckException;
import com.bakigoal.spring.security.annotation.Allow;
import com.bakigoal.spring.security.Auth;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Интерцептор для проверки прав доступа
 */
@Aspect
@Component
@Slf4j
public class SecurityInterceptor {

    /**
     * @param allow аннотация @Allow на проверяемом методе
     * @throws AccessCheckException если нет прав доступа к методу
     */
    @Before("execution(public * com.bakigoal.spring.service..*(..)) && @annotation(allow)")
    public void checkPermissions(Allow allow) {
        MyUserDetails userDetails = Auth.getCurrentUser().orElseThrow(() -> new AccessCheckException("No user"));
        var userRole = userDetails.getRole();
        var hasAccess = List.of(allow.roles()).stream()
                .anyMatch(role -> role.equals(userRole));

        if (!hasAccess) {
            String errorMessage = String.format("User: %s - has NO access", userDetails.getUsername());
            throw new AccessCheckException(errorMessage);
        }
        log.info("User: {} - has access", userDetails.getUsername());
    }
}

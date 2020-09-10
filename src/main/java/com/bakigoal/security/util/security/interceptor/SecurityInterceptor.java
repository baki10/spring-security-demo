package com.bakigoal.security.util.security.interceptor;

import com.bakigoal.security.config.MyUserDetails;
import com.bakigoal.security.exception.AccessCheckException;
import com.bakigoal.security.util.security.Auth;
import com.bakigoal.security.util.security.annotation.Allow;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @Before("execution(public * com.bakigoal.security.service..*(..)) && @annotation(allow)")
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

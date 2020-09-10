package com.bakigoal.security.util.security.annotation;

import com.bakigoal.security.domain.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Права доступа к методу
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Allow {

    /**
     * Список ролей, для которых доступен метод
     */
    Role[] roles();
}

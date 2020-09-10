package com.bakigoal.spring.config.security.basic;

import com.bakigoal.spring.config.security.AbstractSecurityConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@ConditionalOnProperty(name = "app.security.type", havingValue = "basic")
public class BasicSecurityConfig extends AbstractSecurityConfig {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.httpBasic();
    }
}

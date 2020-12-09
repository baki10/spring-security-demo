package com.bakigoal.spring.config.security.jwt;

import com.bakigoal.spring.config.security.AbstractSecurityConfig;
import com.bakigoal.spring.config.security.jwt.exception.JwtAuthenticationEntryPoint;
import com.bakigoal.spring.config.security.jwt.filter.JwtRequestFilter;
import com.bakigoal.spring.config.security.jwt.provider.JwtAuthenticationProvider;
import com.bakigoal.spring.controller.JwtAuthController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@ConditionalOnProperty(name = "app.security.type", havingValue = "jwt")
@AllArgsConstructor
public class JwtSecurityConfig extends AbstractSecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder())
                .and()
                .authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        super.configure(web);
        web.ignoring().antMatchers(JwtAuthController.JWT_AUTH_URL);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        // Add a filter to validate the tokens with every request
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

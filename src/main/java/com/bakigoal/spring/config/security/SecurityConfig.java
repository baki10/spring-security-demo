package com.bakigoal.spring.config.security;

import com.bakigoal.spring.config.security.exception.MyAuthenticationEntryPoint;
import com.bakigoal.spring.config.security.common.UsernamePasswordAuthProvider;
import com.bakigoal.spring.config.security.jwt.filter.JwtRequestFilter;
import com.bakigoal.spring.config.security.jwt.provider.JwtAuthenticationProvider;
import com.bakigoal.spring.controller.JwtAuthController;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final UsernamePasswordAuthProvider usernamePasswordAuthProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(usernamePasswordAuthProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/", JwtAuthController.JWT_AUTH_URL).permitAll()
                .and().authorizeRequests().antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .and().authorizeRequests().antMatchers("/user", "/roles/**", "/**").authenticated();

        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint);

        // support both BASIC and Token-based authentication
        http.httpBasic();
        http.addFilterBefore(jwtRequestFilter, BasicAuthenticationFilter.class);
    }
}

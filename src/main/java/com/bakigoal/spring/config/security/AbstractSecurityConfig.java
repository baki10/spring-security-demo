package com.bakigoal.spring.config.security;

import com.bakigoal.spring.config.security.common.MyUserDetails;
import com.bakigoal.spring.domain.DbRole;
import com.bakigoal.spring.domain.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
public abstract class AbstractSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/user", "/roles/**").authenticated()
                .and().authorizeRequests().antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .and().authorizeRequests().antMatchers("/token").permitAll()
                .and().anonymous(anonymousConfigurer());
    }

    private Customizer<AnonymousConfigurer<HttpSecurity>> anonymousConfigurer() {
        return anonymousConfigurer -> anonymousConfigurer.principal(
                new MyUserDetails("anonymous", Collections.singletonList(Role.ROLE_ANONYMOUS))
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.bakigoal.security.config;

import com.bakigoal.security.domain.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                .and().authorizeRequests().antMatchers("/user").authenticated()
                .and().authorizeRequests().antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .and().authorizeRequests().antMatchers("/token").permitAll()
                .and().anonymous(anonymousConfigurer());
    }

    private Customizer<AnonymousConfigurer<HttpSecurity>> anonymousConfigurer() {
        return anonymousConfigurer -> anonymousConfigurer.principal(
                new User("anonymous", "anonymous",
                        Collections.singletonList(new SimpleGrantedAuthority(Role.ROLE_ANONYMOUS.name()))
                )
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

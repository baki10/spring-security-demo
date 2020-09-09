package com.bakigoal.security.config.basic;

import com.bakigoal.security.domain.MyAuthority;
import com.bakigoal.security.repository.UserRepo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@ConditionalOnProperty(name = "app.security.type", havingValue = "basic")
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/user").authenticated()
                .and().authorizeRequests().antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .and().authorizeRequests().antMatchers("/public/**").permitAll()
                .and().httpBasic()
                .and().anonymous(anonymousConfigurer());
    }

    private Customizer<AnonymousConfigurer<HttpSecurity>> anonymousConfigurer() {
        return anonymousConfigurer -> anonymousConfigurer.principal(
                new User("anonymous", "anonymous",
                        Collections.singletonList(new SimpleGrantedAuthority(MyAuthority.ROLE_ANONYMOUS.name()))
                )
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo) {
        return new BasicUserDetailsService(userRepo);
    }
}

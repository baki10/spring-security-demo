package com.bakigoal.spring.config.security.jwt.filter;

import com.bakigoal.spring.config.security.jwt.provider.JwtAuthentication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Component
@ConditionalOnProperty(name = "app.security.type", havingValue = "jwt")
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        getBearerToken(request).ifPresent(
                token -> {
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(token));
                    }
                });
        chain.doFilter(request, response);
    }

    private Optional<String> getBearerToken(HttpServletRequest request) {
        final String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null || !tokenHeader.startsWith(BEARER_PREFIX)) {
            log.warn("JWT Token does not begin with Bearer String");
            return Optional.empty();
        }
        return Optional.of(tokenHeader.substring(BEARER_PREFIX.length()));
    }
}
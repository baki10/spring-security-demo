package com.bakigoal.spring.config.security.jwt;

import com.bakigoal.spring.config.security.common.MyUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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

    private final MyUserDetailsService myUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String tokenHeader = request.getHeader("Authorization");
        var token = getBearerToken(tokenHeader);
        if (token.isPresent()) {
            var username = extractUsernameFromToken(token.get());
            if (username.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = this.myUserDetailsService.loadUserByUsername(username.get());
                if (jwtTokenUtil.validateToken(token.get(), user)) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Authenticated: {}",user.getUsername());
                }
            }
        }
        chain.doFilter(request, response);
    }

    private Optional<String> getBearerToken(String tokenHeader) {
        if (tokenHeader == null || !tokenHeader.startsWith(BEARER_PREFIX)) {
            log.warn("JWT Token does not begin with Bearer String");
            return Optional.empty();
        }
        return Optional.of(tokenHeader.substring(BEARER_PREFIX.length()));
    }

    private Optional<String> extractUsernameFromToken(String token) {
        try {
            return Optional.of(jwtTokenUtil.getUsernameFromToken(token));
        } catch (IllegalArgumentException e) {
            log.warn("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            log.warn("JWT Token has expired");
        }
        return Optional.empty();
    }
}
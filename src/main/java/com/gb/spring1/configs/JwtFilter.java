package com.gb.spring1.configs;


import com.gb.spring1.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (Objects.nonNull(authorizationHeaderValue) && authorizationHeaderValue.startsWith("Bearer ")) {
                String bearerTokenValue = authorizationHeaderValue.substring(7);

                String username = jwtTokenUtil.getUsernameFromToken(bearerTokenValue);
                List<? extends GrantedAuthority> authorities =  jwtTokenUtil.getRoles(bearerTokenValue);

                if (Objects.nonNull(username)) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(username, null, authorities)
                    );
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

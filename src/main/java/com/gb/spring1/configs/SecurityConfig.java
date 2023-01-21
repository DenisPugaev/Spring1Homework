package com.gb.spring1.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.http.HttpRequest;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring().requestMatchers("/api/v1/**","/api/v1/auth/token/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(JwtFilter filter, HttpSecurity security) throws Exception {
        return security.authorizeHttpRequests()
                .requestMatchers("/api/v1/profile/**").authenticated()
                .requestMatchers( "/api/v1/admin/**").authenticated()
                .requestMatchers("/api/v1/profile/**").hasAnyRole("ROLE_USER")
                .requestMatchers("/api/v1/admin/product/**").hasAnyRole("ROLE_ADMIN")

                .anyRequest().permitAll()
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("pass")
//                .authorities("ADMIN", "MANAGER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider... providers) {
        return new ProviderManager(providers);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new StandardAuthenticationProvider();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}



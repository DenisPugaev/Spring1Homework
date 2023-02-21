package com.gb.web.auth.controllers;



import com.gb.web.auth.dto.JwtRequest;
import com.gb.web.auth.dto.JwtResponse;
import com.gb.web.auth.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController

public class AuthController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/token")
    public JwtResponse auth(@RequestBody JwtRequest request) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails user = (UserDetails) authenticate.getPrincipal();
        String token = jwtTokenUtil.generateToken(user);
        return new JwtResponse(token);
    }

}

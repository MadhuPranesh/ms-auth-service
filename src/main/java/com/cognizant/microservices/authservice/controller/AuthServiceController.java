package com.cognizant.microservices.authservice.controller;

import com.cognizant.microservices.authservice.exceptions.TokenInvalidException;
import com.cognizant.microservices.authservice.model.LoginUser;
import com.cognizant.microservices.authservice.model.TokenResponse;
import com.cognizant.microservices.authservice.security.TokenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthServiceController extends ResponseEntityExceptionHandler {

    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticateUser(@RequestBody LoginUser loginUser){
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getUserName(), loginUser.getPassword()));
        String token = tokenHandler.generateToken(loginUser.getUserName());
        TokenResponse tokenResponse = new TokenResponse(loginUser.getUserName(), token);
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }
    @GetMapping("/validateToken")
    public boolean validateToken(@RequestHeader("Authorization") String token) throws TokenInvalidException {
        return tokenHandler.validateToken(token.substring(7));
    }

}

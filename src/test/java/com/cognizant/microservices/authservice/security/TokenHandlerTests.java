package com.cognizant.microservices.authservice.security;

import com.cognizant.microservices.authservice.exceptions.TokenInvalidException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class TokenHandlerTests {

    @Autowired
    TokenHandler tokenHandler;

    @Test
    void testGetUsernameFromToken() {

        log.info("********* testing the util of extracting username from jwt *********");
        final String username = "someUserName";
        String token = tokenHandler.generateToken(username);
        assertEquals(username, tokenHandler.extractUsername(token));

    }

    @Test
    void testInvalidExpiredToken() {
        log.info("************ testing the expired token ***********");

        final String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZWRkeSIsInN1YiI6InRlZGR5IiwiaWF0IjoxNjYwMDUyNTcwLCJleHAiOjE2NjAwNTI2MzB9.HjCykDZHZ3b0NZyuULzEgfPHuALlPJggE318PYe7sAo";
        TokenInvalidException thrownException = assertThrows(TokenInvalidException.class, () -> tokenHandler.validateToken(token));
    }

    @Test
    void testInvalidTamperedToken() {
        log.info("********** testing the tampered/altered token **********");

        final String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZWRkeSIsInN1YiIcdv6InRlZGR5IiwiaWF0IjoxNjYwMDUyNTcwLCJleHAiOjE2NjAwNTI2MzBfs9.HjCykDZHZ3b0NZyuULzEgfPHuALlPJggE318PYe7sAo";
        TokenInvalidException thrownException = assertThrows(TokenInvalidException.class, () -> tokenHandler.validateToken(token));

    }

    @Test
    void testMalformedToken() {
        log.info("********** testing the tampered/altered token **********");

        final String token = "someMalformedToken";
        TokenInvalidException thrownException = assertThrows(TokenInvalidException.class, () -> tokenHandler.validateToken(token));

    }

}

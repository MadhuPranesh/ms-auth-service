package com.cognizant.microservices.authservice.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
@Slf4j
public class OrganizationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InternalAuthenticationServiceException.class,BadCredentialsException.class})
    public final ResponseEntity<Object> handleUsernameNotFoundException(final Exception ex, final WebRequest request) throws Exception {

        if(ex instanceof BadCredentialsException){
            log.error("********** Bad credentials provided by the user **********");
        }
        else{
            log.error("********** "+ex.getMessage()+" **********");
        }
        OrganizationExceptionResponse exceptionResponse = new OrganizationExceptionResponse(new Date(), "Invalid Credentials provided by the User","Invalid Credentials!!!");
        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(TokenInvalidException.class)
    public final ResponseEntity<OrganizationExceptionResponse> handleTokenInvalidException(Exception ex, WebRequest request) throws Exception {
        OrganizationExceptionResponse exceptionResponse = new OrganizationExceptionResponse(new Date(), "Invalid Credentials provided by the User","Invalid Credentials!!!");
        return new ResponseEntity(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<OrganizationExceptionResponse> handleAllExceptions(Exception ex) {
        OrganizationExceptionResponse e = new OrganizationExceptionResponse(new Date(), ex.getMessage(), "Something went wrong");
        return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

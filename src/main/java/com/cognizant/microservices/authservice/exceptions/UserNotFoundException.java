package com.cognizant.microservices.authservice.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userName) {
        super("Invalid User with user name : " + userName);
    }
}

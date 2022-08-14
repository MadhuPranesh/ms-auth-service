package com.cognizant.microservices.authservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class TokenResponse {

    private String userName;

    private String token;
}

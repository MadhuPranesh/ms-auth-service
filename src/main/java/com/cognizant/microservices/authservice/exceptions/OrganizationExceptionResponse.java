package com.cognizant.microservices.authservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@AllArgsConstructor
@Data
public class OrganizationExceptionResponse {

    private Date date;
    private String message;
    private String details;
}


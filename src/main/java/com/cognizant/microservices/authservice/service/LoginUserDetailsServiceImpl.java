package com.cognizant.microservices.authservice.service;

import com.cognizant.microservices.authservice.exceptions.UserNotFoundException;
import com.cognizant.microservices.authservice.model.LoginUser;
import com.cognizant.microservices.authservice.model.LoginUserPrincipal;
import com.cognizant.microservices.authservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LoginUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Optional<LoginUser> usersListBasedOnUserName = customerRepository.findByUserName(username);
        log.info("*********** loaded username from the database *************");

        if(!usersListBasedOnUserName.isPresent()){
            log.error("************ usre name not found in the database ***********");
            throw new UserNotFoundException(username);
        }
        log.info("************** username found => passing on to validate credentials *********");
       return new LoginUserPrincipal(usersListBasedOnUserName.get());
    }
}

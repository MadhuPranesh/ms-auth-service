package com.cognizant.microservices.authservice.service;

import com.cognizant.microservices.authservice.exceptions.UserNotFoundException;
import com.cognizant.microservices.authservice.model.LoginUser;
import com.cognizant.microservices.authservice.model.LoginUserPrincipal;
import com.cognizant.microservices.authservice.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
public class LoginUserDetailsServiceImplTests {

    @Autowired
    private LoginUserDetailsServiceImpl loginUserDetailsService;

    @MockBean
    private CustomerRepository customerRepository;


    @Test
    void testingtheValiduserCredentials() {
        log.info("*********** testing the valid user creds  ************");

        LoginUser user = new LoginUser("user", "someUserName", "somePassword");
        Optional<LoginUser> userOptional = Optional.of(user);
        LoginUserPrincipal userPrincipal = new LoginUserPrincipal(user);

        when(customerRepository.findByUserName(user.getUserName())).thenReturn(userOptional);
        assertEquals(loginUserDetailsService.loadUserByUsername(user.getUserName()).getPassword(), userPrincipal.getPassword());

    }

    @Test
    void testInvalidUserCredentails() {
        log.info("*********** testing the invalid creds of the user ***********");

        Optional<LoginUser> userOptional = Optional.empty();
        final String invalidUser = "invalidUser";
        when(customerRepository.findByUserName(invalidUser)).thenReturn(userOptional);

        UserNotFoundException thrownException = assertThrows(UserNotFoundException.class,
                () -> loginUserDetailsService.loadUserByUsername(invalidUser));

    }


}

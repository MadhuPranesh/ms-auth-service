package com.cognizant.microservices.authservice.repository;

import com.cognizant.microservices.authservice.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
@TestPropertySource(properties = {
        "eureka.client.enabled=false"
})
class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testingtheValidUser() {
        log.info("********* testing the valid username *************");
        final String username = "teddy";
        Optional<LoginUser> userCheck = customerRepository.findByUserName(username);
        assertTrue(userCheck.isPresent());
        assertEquals(username, userCheck.get().getUserName());
    }

    @Test
    void testingtheInValidUser() {
        log.info("********* testing the invalid username *************");
        final String username = "invalidusername";
        Optional<LoginUser> invalidUserCheck = customerRepository.findByUserName(username);
        assertFalse(invalidUserCheck.isPresent());
    }
    }

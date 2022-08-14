package com.cognizant.microservices.authservice.controller;

import com.cognizant.microservices.authservice.exceptions.TokenInvalidException;
import com.cognizant.microservices.authservice.model.LoginUser;
import com.cognizant.microservices.authservice.model.LoginUserPrincipal;
import com.cognizant.microservices.authservice.security.TokenHandler;
import com.cognizant.microservices.authservice.service.LoginUserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Slf4j
class AuthorizationControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginUserDetailsServiceImpl loginUserDetailsService;

    @MockBean
    private TokenHandler tokenHandler;

    @MockBean
    private AuthenticationManager authenticationManager;

    private static ObjectMapper mapper = new ObjectMapper();


    @BeforeEach()
    public void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testLoginAPIwithvalidCreds() throws Exception {

        log.info("********** test login api url with valid creds ***************");
        //say a valid user
        LoginUser user = new LoginUser("user", "someUserName", "somePassword");

        String dummyToken = "someValidToken";
        when(authenticationManager.authenticate(ArgumentMatchers.any()))
                .thenReturn(new TestingAuthenticationToken("someUserName", "somePassword", "CUSTOMER"));
        when(tokenHandler.generateToken(ArgumentMatchers.any())).thenReturn(dummyToken);

        String jsonObj = mapper.writeValueAsString(user);

        MvcResult result = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON)
                                  .content(jsonObj)).andExpect(status().isOk())
                                  .andReturn();

        String response = result.getResponse().getContentAsString();;
        JSONObject  jsonResponse =new JSONObject(response);

        assertEquals(jsonResponse.get("userName"),"someUserName");
        assertEquals(jsonResponse.get("token"),dummyToken);
    }

    @Test
    void testLoginAPIwithInvalidCreds() throws Exception {

        log.info("*********** testing the login api url with invalid creds ***********");
        // say an invalid user
        LoginUser user = new LoginUser("user", "someUserName", "someInvalidPassword");

        when(authenticationManager.authenticate(ArgumentMatchers.any())).thenThrow(new BadCredentialsException("Invalid Credentials!!!"));

        String jsonObj = mapper.writeValueAsString(user);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON).content(jsonObj))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testValidTokenviaheader() throws Exception {

        log.info("****** testing invalid token sent via header to a secured url *********");
        LoginUser user = new LoginUser("user", "someUserName", "somePassword");
        LoginUserPrincipal userPrincipal = new LoginUserPrincipal(user);


        when(loginUserDetailsService.loadUserByUsername(ArgumentMatchers.any())).thenReturn(userPrincipal);
        when(tokenHandler.validateToken(ArgumentMatchers.any())).thenReturn(true);

        String token = "Bearer someValidToken";
        mockMvc.perform(get("/auth/validateToken").header(HttpHeaders.AUTHORIZATION, token)).andExpect(status().isOk());
    }

    @Test
    void testInvalidTokenviaheader() throws Exception {
        log.info("****** testing invalid token sent via header to a secured url *********");

        when(tokenHandler.validateToken(ArgumentMatchers.any())).thenThrow(new TokenInvalidException("Invalid JWT auth token !!!"));
        String token = "Bearer someToken";

        mockMvc.perform(get("/auth/validateToken").header(HttpHeaders.AUTHORIZATION, token)).andExpect(status().isUnauthorized());
    }
}


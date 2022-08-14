package com.cognizant.microservices.authservice;

import com.cognizant.microservices.authservice.controller.AuthServiceController;
import com.cognizant.microservices.authservice.model.LoginUser;
import com.cognizant.microservices.authservice.model.LoginUserPrincipal;
import com.cognizant.microservices.authservice.model.TokenResponse;
import com.cognizant.microservices.authservice.repository.CustomerRepository;
import com.cognizant.microservices.authservice.security.TokenHandler;
import com.cognizant.microservices.authservice.service.LoginUserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@WebMvcTest(AuthServiceController.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthServiceApplicationTests {

	@Test
	void contextLoads() {
	}
//
//
////	@Test
////	void testApiCall() throws Exception {
////		String uri = "/auth/login";
////		LoginUser loginUser = new LoginUser(null,null,"teddy","ted@123");
////		ObjectMapper objectMapper = new ObjectMapper();
////		String cred= objectMapper.writeValueAsString(loginUser);
////		MockMvc mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
////
////		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
////				.contentType(MediaType.APPLICATION_JSON_VALUE).content(cred)).andReturn();
////		int status = mvcResult.getResponse().getStatus();
////		String content = mvcResult.getResponse().getContentAsString();
////		assertEquals(200, status);
////
////
////
////	}
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private TokenHandler tokenHandler;
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	@MockBean
//	private LoginUserDetailsServiceImpl loginUserDetailsService;
//
//
//	@Test
//	public void testValidCredentials() throws Exception {
//
//		String stubUsername= "dummyUsername";
//		when(loginUserDetailsService.loadUserByUsername(stubUsername)).thenReturn(new LoginUserPrincipal(new LoginUser(null,null,stubUsername,"$2a$12$91tbz1rOAhT0Ozpw8uHyQOaFWLGt0DSNe6UoHZJMzRL5dygO4a/aO")));
//
//		LoginUser loginUser = new LoginUser(null,null,stubUsername,"dummy");
//		ObjectMapper objectMapper = new ObjectMapper();
//		String cred= objectMapper.writeValueAsString(loginUser);
//		this.mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(cred)).andDo(print()).andExpect(status().isOk());
//	}
//
//	@Test
//	public void testInValidCredentials() throws Exception {
//
//		String stubUsername= "dummyUsername";
//		when(loginUserDetailsService.loadUserByUsername(stubUsername)).thenReturn(new LoginUserPrincipal(new LoginUser(null,null,stubUsername,"$2a$12$91tbz1rOAhT0Ozpw8uHyQOaFWLGt0DSNe6UoHZJMzRL5dygO4a/a") ));
//
//		LoginUser loginUser = new LoginUser(null,null,stubUsername,"invaliddummy");
//		ObjectMapper objectMapper = new ObjectMapper();
//		String cred= objectMapper.writeValueAsString(loginUser);
//		this.mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(cred)).andDo(print()).andExpect(status().isUnauthorized());
//	}

}

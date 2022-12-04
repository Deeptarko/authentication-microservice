package com.project.authenticationmicroservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.authenticationmicroservice.entity.User;
import com.project.authenticationmicroservice.repository.UserRepository;
import com.project.authenticationmicroservice.service.UserService;
import com.project.authenticationmicroservice.utils.JWTUtil;

@WebMvcTest(controllers = AuthenticationController.class)
class AuthenticationControllerTest {

	@MockBean
	private UserDetailsService userService1;
	@MockBean
	private UserService userService;
	@MockBean
	private JWTUtil jwtUtil;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private BCryptPasswordEncoder passwordEncoder;
	@MockBean
	private UserRepository userRepository;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private AuthenticationManager authenticationManager;

	@Test
	public void shouldSaveUser() throws Exception {

		User user = new User();
		user.setId(1);
		user.setName("Deep");
		user.setPassword("1234");
		user.setRoles(Set.of("Admin", "User"));
		String token = "feygbeifbef";
		String username = "Deep";
		Mockito.when(userService.saveUser(user)).thenReturn(user);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/user/save").header("Authorization", token).header("Username", username)
						.content(objectMapper.writeValueAsString(user)).contentType(MediaType.APPLICATION_JSON))

				.andExpect(MockMvcResultMatchers.status().is(201)).andDo(print());

	}
}

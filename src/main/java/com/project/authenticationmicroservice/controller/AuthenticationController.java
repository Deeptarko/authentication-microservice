package com.project.authenticationmicroservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.project.authenticationmicroservice.dto.LoginRequestDTO;
import com.project.authenticationmicroservice.dto.LoginResponseDTO;
import com.project.authenticationmicroservice.entity.User;
import com.project.authenticationmicroservice.service.UserService;
import com.project.authenticationmicroservice.utils.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/user")
public class AuthenticationController {

	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(),
				request.getPassword());
		log.info(request.getUsername() + ":" + request.getPassword());
		Authentication auth = authenticationManager.authenticate(token);
		String jwtToken = jwtUtil.generateToken(request.getUsername());
		List<GrantedAuthority> roles = (List) auth.getAuthorities();
		List<String> rolesResponse = roles.stream().map((authority) -> authority.getAuthority())
				.collect(Collectors.toList());
		LoginResponseDTO response = new LoginResponseDTO(jwtToken, auth.getName(), rolesResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/save")
	public ResponseEntity<Map<String, String>> saveUser(@RequestBody User user) {

		User userSaved = userService.saveUser(user);
		Integer id = userSaved.getId();
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "User with the id " + id + " created");
		return new ResponseEntity<>(map, HttpStatus.CREATED);
	}
}

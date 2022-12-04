package com.project.authenticationmicroservice.service;

import java.util.Optional;

import com.project.authenticationmicroservice.entity.User;

public interface UserService {
	public User saveUser(User user);
	public Optional<User> findByUsername(String username);
}

package com.project.authenticationmicroservice.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.project.authenticationmicroservice.entity.User;

@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRespository;

	@Test
	public void saveUserTest() {
		User user = new User();
		user.setName("deep");
		user.setPassword("1234");
		user.setRoles(Set.of("ADMIN", "ROLES"));
		user.setUsername("deep1234");
		userRespository.save(user);
		Assertions.assertThat(user.getId()).isGreaterThan(0);
	}

	@Test
	public void findUserByUsernameTest() {
		User user = new User();
		user.setName("deep");
		user.setPassword("1234");
		user.setRoles(Set.of("ADMIN", "ROLES"));
		user.setUsername("deep1234");
		userRespository.save(user);
		User savedUser = userRespository.findByUsername("deep1234").get();
		Assertions.assertThat(user.getId()).isEqualTo(savedUser.getId());
	}
}

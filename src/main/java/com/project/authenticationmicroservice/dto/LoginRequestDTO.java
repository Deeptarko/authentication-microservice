package com.project.authenticationmicroservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDTO {
	private String username;
	private String password;
}

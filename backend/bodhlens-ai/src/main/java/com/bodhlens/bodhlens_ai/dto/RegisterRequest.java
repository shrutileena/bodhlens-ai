package com.bodhlens.bodhlens_ai.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
		
		@NotBlank(message = "First name is required")
		@Size(max = 50)
		String firstName,
	
		@NotBlank(message = "Last name is required")
		@Size(max = 50)
		String lastName,
		
		@NotBlank(message = "Email is required")
		@Email(message = "Invalid email")
		@Size(max = 255)
		String email,
		
		@NotBlank(message = "Password is required")
		@Size(min = 8, max = 100)
		String password) {
}

package com.documind.documind_ai.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.documind.documind_ai.dto.LoginRequest;
import com.documind.documind_ai.dto.LoginResponse;
import com.documind.documind_ai.dto.RegisterRequest;
import com.documind.documind_ai.dto.RegisterResponse;
import com.documind.documind_ai.service.AuthService;
import com.documind.documind_ai.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final UserService userService;
	
	private final AuthService authService;
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
		return userService.register(request);
	}
	
	@PostMapping("/login")
	public LoginResponse login(@Valid @RequestBody LoginRequest request) {
		return authService.login(request);
	}

}

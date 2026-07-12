package com.documind.documind_ai.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.documind.documind_ai.dto.LoginRequest;
import com.documind.documind_ai.dto.LoginResponse;
import com.documind.documind_ai.entity.User;
import com.documind.documind_ai.exception.InvalidCredentialsException;
import com.documind.documind_ai.repository.UserRepository;
import com.documind.documind_ai.security.JwtService;
import com.documind.documind_ai.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;

	@Override
	public LoginResponse login(LoginRequest request) {
		
		User user = userRepository.findByEmail(request.email()).orElseThrow(() -> 
			new InvalidCredentialsException("Invalid email or password"));
		
		if(!passwordEncoder.matches(request.password(), user.getPassword())) {
			throw new InvalidCredentialsException("Invalid email or password");
		}
		
		String token = jwtService.generateToken(user.getEmail());
		
		return new LoginResponse(
				token, 
				user.getId(), 
				user.getFirstName(), 
				user.getLastName(), 
				user.getEmail());
	}

	
}

package com.bodhlens.bodhlens_ai.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bodhlens.bodhlens_ai.dto.LoginRequest;
import com.bodhlens.bodhlens_ai.dto.LoginResponse;
import com.bodhlens.bodhlens_ai.entity.User;
import com.bodhlens.bodhlens_ai.exception.InvalidCredentialsException;
import com.bodhlens.bodhlens_ai.repository.UserRepository;
import com.bodhlens.bodhlens_ai.security.JwtService;
import com.bodhlens.bodhlens_ai.service.AuthService;

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

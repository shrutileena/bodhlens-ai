package com.documind.documind_ai.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.documind.documind_ai.dto.RegisterRequest;
import com.documind.documind_ai.dto.RegisterResponse;
import com.documind.documind_ai.entity.User;
import com.documind.documind_ai.enums.Role;
import com.documind.documind_ai.exception.EmailAlreadyExistsException;
import com.documind.documind_ai.repository.UserRepository;
import com.documind.documind_ai.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public RegisterResponse register(RegisterRequest request) {
		if(userRepository.existsByEmail(request.email())) {
			throw new EmailAlreadyExistsException("Email already exists");
		}
		
		User user = new User();
		user.setFirstName(request.firstName());
		user.setLastName(request.lastName());
		user.setEmail(request.email());
		user.setPassword(passwordEncoder.encode(request.password()));
		user.setRole(Role.USER);
		
		User savedUser = userRepository.save(user);
		return new RegisterResponse(savedUser.getId(),
				savedUser.getFirstName(), 
				savedUser.getLastName(), 
				savedUser.getEmail(), 
				"User registered successfully", 
				savedUser.getCreatedAt());
	}
}

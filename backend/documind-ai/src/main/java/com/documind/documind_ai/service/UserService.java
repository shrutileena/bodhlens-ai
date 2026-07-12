package com.documind.documind_ai.service;

import com.documind.documind_ai.dto.RegisterRequest;
import com.documind.documind_ai.dto.RegisterResponse;

public interface UserService {

	RegisterResponse register(RegisterRequest request);
}

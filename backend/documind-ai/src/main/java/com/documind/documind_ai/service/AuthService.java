package com.documind.documind_ai.service;

import com.documind.documind_ai.dto.LoginRequest;
import com.documind.documind_ai.dto.LoginResponse;

public interface AuthService {

	LoginResponse login(LoginRequest request);
}

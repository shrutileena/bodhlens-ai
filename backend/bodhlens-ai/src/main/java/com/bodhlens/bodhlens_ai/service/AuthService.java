package com.bodhlens.bodhlens_ai.service;

import com.bodhlens.bodhlens_ai.dto.LoginRequest;
import com.bodhlens.bodhlens_ai.dto.LoginResponse;

public interface AuthService {

	LoginResponse login(LoginRequest request);
}

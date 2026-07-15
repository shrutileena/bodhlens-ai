package com.bodhlens.bodhlens_ai.service;

import com.bodhlens.bodhlens_ai.dto.RegisterRequest;
import com.bodhlens.bodhlens_ai.dto.RegisterResponse;

public interface UserService {

	RegisterResponse register(RegisterRequest request);
}

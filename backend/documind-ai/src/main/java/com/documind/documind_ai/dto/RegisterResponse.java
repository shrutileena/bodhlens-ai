package com.documind.documind_ai.dto;

import java.time.Instant;
import java.util.UUID;

public record RegisterResponse(
		
		UUID id,
		String firstName,
		String lastName,
		String email,
		String message,
		Instant registeredAt
		) {

}

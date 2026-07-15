package com.bodhlens.bodhlens_ai.dto;

import java.util.UUID;

public record LoginResponse(
		
		String token,
		UUID userId,
		String firstName,
		String lastName,
		String email
		) {

}

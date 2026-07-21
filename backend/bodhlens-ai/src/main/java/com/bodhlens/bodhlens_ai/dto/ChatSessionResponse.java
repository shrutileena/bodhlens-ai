package com.bodhlens.bodhlens_ai.dto;

import java.time.Instant;
import java.util.UUID;

public record ChatSessionResponse(
		UUID id,
		String title,
		Instant updatedAt
		) {

}

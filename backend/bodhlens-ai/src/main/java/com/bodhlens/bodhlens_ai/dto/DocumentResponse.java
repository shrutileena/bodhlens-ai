package com.bodhlens.bodhlens_ai.dto;

import java.time.Instant;
import java.util.UUID;

import com.bodhlens.bodhlens_ai.enums.DocumentStatus;

public record DocumentResponse(
		UUID id,
		String fileName,
		DocumentStatus status,
		Instant createdAt
		) {

}

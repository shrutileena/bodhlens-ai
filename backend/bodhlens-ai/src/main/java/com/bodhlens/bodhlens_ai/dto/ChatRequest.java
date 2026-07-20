package com.bodhlens.bodhlens_ai.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
		
		@NotBlank
		String question
		
		) {

}

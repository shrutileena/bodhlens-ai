package com.bodhlens.bodhlens_ai.ai.dto;

public record OllamaRequest(
		String model,
		String prompt,
		boolean stream 
		) {

}

package com.bodhlens.bodhlens_ai.ai;

import org.springframework.stereotype.Service;

@Service
public class GeminiService implements LLMService {

	@Override
	public String askQuestion(LLMRequest request) {
		return "Gemini not integrated yet";
	}

}

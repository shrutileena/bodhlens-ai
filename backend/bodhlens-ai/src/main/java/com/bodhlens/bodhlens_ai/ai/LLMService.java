package com.bodhlens.bodhlens_ai.ai;

public interface LLMService {

	String askQuestion(LLMRequest request);
	
	String generateTitle(String question);
}

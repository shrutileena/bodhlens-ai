package com.bodhlens.bodhlens_ai.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LLMServiceFactory {

	private final OllamaService ollamaService;
	
	private final GeminiService geminiService;
	
	@Value("${ai.provider}")
	private String provider;
	
	public LLMServiceFactory(OllamaService ollamaService, 
			GeminiService geminiService) {
		this.ollamaService = ollamaService;
		this.geminiService = geminiService;
	}
	
	public LLMService getService() {
		if(AIProvider.GEMINI.name().equalsIgnoreCase(provider)) {
			return geminiService;
		}
		
		return ollamaService;
	}
	
}

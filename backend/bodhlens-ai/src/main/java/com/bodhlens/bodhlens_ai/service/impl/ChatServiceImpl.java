package com.bodhlens.bodhlens_ai.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bodhlens.bodhlens_ai.ai.LLMRequest;
import com.bodhlens.bodhlens_ai.ai.LLMServiceFactory;
import com.bodhlens.bodhlens_ai.dto.ChatRequest;
import com.bodhlens.bodhlens_ai.dto.ChatResponse;
import com.bodhlens.bodhlens_ai.service.ChatService;
import com.bodhlens.bodhlens_ai.util.DocumentTextExtractor;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	DocumentTextExtractor documentTextExtractor;
	
	@Autowired
	LLMServiceFactory llmServiceFactory;

	@Override
	public ChatResponse chat(UUID documentId, ChatRequest request) throws IOException, InterruptedException {
//		Thread.sleep(3000);
//		return new ChatResponse("You asked: " + request.question());
		String documentText = documentTextExtractor.extractText(documentId);
		
		System.out.println("documentText - " + documentText);
		
		LLMRequest llmRequest = new LLMRequest(documentText, request.question());
		
		String answer = llmServiceFactory.getService().askQuestion(llmRequest);
		
		System.out.println("answer - "+ answer);
		
		return new ChatResponse(answer);
	}
}

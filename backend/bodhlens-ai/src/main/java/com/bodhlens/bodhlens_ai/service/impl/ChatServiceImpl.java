package com.bodhlens.bodhlens_ai.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bodhlens.bodhlens_ai.ai.LLMRequest;
import com.bodhlens.bodhlens_ai.ai.LLMServiceFactory;
import com.bodhlens.bodhlens_ai.dto.ChatMessageResponse;
import com.bodhlens.bodhlens_ai.dto.ChatRequest;
import com.bodhlens.bodhlens_ai.dto.ChatResponse;
import com.bodhlens.bodhlens_ai.entity.ChatMessage;
import com.bodhlens.bodhlens_ai.entity.ChatSession;
import com.bodhlens.bodhlens_ai.entity.Document;
import com.bodhlens.bodhlens_ai.enums.MessageSender;
import com.bodhlens.bodhlens_ai.repository.ChatMessageRepository;
import com.bodhlens.bodhlens_ai.repository.ChatSessionRepository;
import com.bodhlens.bodhlens_ai.service.ChatService;
import com.bodhlens.bodhlens_ai.util.DocumentTextExtractor;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	DocumentTextExtractor documentTextExtractor;
	
	@Autowired
	LLMServiceFactory llmServiceFactory;
	
	@Autowired
	ChatSessionRepository chatSessionRepository;
	
	@Autowired
	ChatMessageRepository chatMessageRepository;
	
	private static final String DEFAULT_CHAT_TITLE = "New Chat";

	@Override
	public ChatResponse chat(UUID sessionId, ChatRequest request) throws IOException, InterruptedException {
//		Thread.sleep(3000);
//		return new ChatResponse("You asked: " + request.question());
		
		long start = System.currentTimeMillis();
		
		ChatSession chatSession = chatSessionRepository.findById(sessionId)
				.orElseThrow(() -> new RuntimeException("Chat session not found"));
		
		ChatMessage userMessage = new ChatMessage();
		userMessage.setChatSession(chatSession);
		userMessage.setSender(MessageSender.USER);
		userMessage.setMessage(request.question());
		
		chatMessageRepository.save(userMessage);
		
		Document document = chatSession.getDocument();
		
		long t1 = System.currentTimeMillis();
		String documentText = documentTextExtractor.extractText(document.getId());
		System.out.println("Document length = " + documentText.length());
		System.out.println("Question = " + request.question());
		System.out.println("Text extraction: " + (System.currentTimeMillis() - t1) + " ms");
		
		long t2 = System.currentTimeMillis();
		LLMRequest llmRequest = new LLMRequest(documentText, request.question());
		
		String answer = llmServiceFactory.getService().askQuestion(llmRequest);
		System.out.println("LLM call: " + (System.currentTimeMillis() - t2) + " ms");
		
		
		long t3 = System.currentTimeMillis();
		ChatMessage aiMessage = new ChatMessage();
		aiMessage.setChatSession(chatSession);
		aiMessage.setSender(MessageSender.AI);
		aiMessage.setMessage(answer);
		
		chatMessageRepository.save(aiMessage);
		System.out.println("DB save: " + (System.currentTimeMillis() - t3) + " ms");
		
		System.out.println("Total: " + (System.currentTimeMillis() - start) + " ms");
		
		if(DEFAULT_CHAT_TITLE.equals(chatSession.getTitle())) {
			String title = llmServiceFactory.getService().generateTitle(request.question());
			chatSession.setTitle(title);
			chatSessionRepository.save(chatSession);
		}
		
		return new ChatResponse(answer);
	}

	@Override
	public List<ChatMessageResponse> getMessages(UUID sessionId) {
		List<ChatMessage> messages = chatMessageRepository.findByChatSessionIdOrderByCreatedAtAsc(sessionId);
		
		return messages.stream().map(msg -> new ChatMessageResponse(
				msg.getSender(), 
				msg.getMessage(), 
				msg.getCreatedAt())).toList();
	}
}

package com.bodhlens.bodhlens_ai.service.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bodhlens.bodhlens_ai.dto.ChatRequest;
import com.bodhlens.bodhlens_ai.dto.ChatResponse;
import com.bodhlens.bodhlens_ai.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

	@Override
	public ChatResponse chat(UUID documentId, ChatRequest request) throws IOException, InterruptedException {
//		Thread.sleep(3000);
		return new ChatResponse("You asked: " + request.question());
	}
}

package com.bodhlens.bodhlens_ai.service;

import java.io.IOException;
import java.util.UUID;

import com.bodhlens.bodhlens_ai.dto.ChatRequest;
import com.bodhlens.bodhlens_ai.dto.ChatResponse;

public interface ChatService {

	ChatResponse chat(UUID documentId, ChatRequest request) throws IOException, InterruptedException;
}

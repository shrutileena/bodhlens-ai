package com.bodhlens.bodhlens_ai.service;

import java.util.List;
import java.util.UUID;

import com.bodhlens.bodhlens_ai.dto.ChatSessionResponse;

public interface ChatSessionService {

	ChatSessionResponse createSession(UUID documentId);
	
	List<ChatSessionResponse> getSessions(UUID documentId);
	
	void deleteSession(UUID sessionId);
}

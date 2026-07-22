package com.bodhlens.bodhlens_ai.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bodhlens.bodhlens_ai.dto.ChatSessionResponse;
import com.bodhlens.bodhlens_ai.entity.ChatSession;
import com.bodhlens.bodhlens_ai.entity.Document;
import com.bodhlens.bodhlens_ai.entity.User;
import com.bodhlens.bodhlens_ai.repository.ChatSessionRepository;
import com.bodhlens.bodhlens_ai.repository.DocumentRepository;
import com.bodhlens.bodhlens_ai.repository.UserRepository;
import com.bodhlens.bodhlens_ai.service.ChatSessionService;

@Service
public class ChatSessionServiceImpl implements ChatSessionService {

	@Autowired
	ChatSessionRepository chatSessionRepository;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ChatSessionResponse createSession(UUID documentId) {
		Document document = documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		
		ChatSession chatSession = new ChatSession();
		
		chatSession.setTitle("New Chat");
		
		chatSession.setDocument(document);
		
		chatSession.setUser(user);
		
		chatSession = chatSessionRepository.save(chatSession);
		
		return new ChatSessionResponse(chatSession.getId(), chatSession.getTitle(), chatSession.getCreatedAt(), chatSession.getUpdatedAt());
	}

	@Override
	public List<ChatSessionResponse> getSessions(UUID documentId) {
		List<ChatSession> sessions = chatSessionRepository.findByDocumentIdOrderByUpdatedAtDesc(documentId);
		return sessions.stream().map(session -> new ChatSessionResponse(
				session.getId(), 
				session.getTitle(), 
				session.getCreatedAt(),
				session.getUpdatedAt())).toList();
	}

	@Override
	public void deleteSession(UUID sessionId) {
		ChatSession session = chatSessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Chat session not found"));
		chatSessionRepository.delete(session);
	}

}

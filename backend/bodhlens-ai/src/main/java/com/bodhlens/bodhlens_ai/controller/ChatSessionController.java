package com.bodhlens.bodhlens_ai.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bodhlens.bodhlens_ai.dto.ChatMessageResponse;
import com.bodhlens.bodhlens_ai.dto.ChatSessionResponse;
import com.bodhlens.bodhlens_ai.dto.DeleteResponse;
import com.bodhlens.bodhlens_ai.service.ChatService;
import com.bodhlens.bodhlens_ai.service.ChatSessionService;

@RestController
@RequestMapping("/api/chat-sessions")
public class ChatSessionController {

	private final ChatSessionService chatSessionService;
	
	private final ChatService chatService;
	
	public ChatSessionController(ChatSessionService chatSessionService, ChatService chatService) {
		this.chatSessionService = chatSessionService;
		this.chatService = chatService;
	}
	
	@PostMapping("/documents/{documentId}")
	public ResponseEntity<ChatSessionResponse> createSession(@PathVariable UUID documentId) {
		ChatSessionResponse chatSessionResponse = chatSessionService.createSession(documentId);
		return ResponseEntity.ok(chatSessionResponse);
	}
	
	@GetMapping("/documents/{documentId}")
	public ResponseEntity<List<ChatSessionResponse>> getSessions(@PathVariable UUID documentId) {
		return ResponseEntity.ok(chatSessionService.getSessions(documentId));
	}
	
	@GetMapping("/{sessionId}/messages")
	public ResponseEntity<List<ChatMessageResponse>> getMessages(@PathVariable UUID sessionId) {
		return ResponseEntity.ok(chatService.getMessages(sessionId));
	}
	
	@DeleteMapping("/{sessionId}")
	public ResponseEntity<DeleteResponse> deleteSession(@PathVariable UUID sessionId) {
		chatSessionService.deleteSession(sessionId);
		return ResponseEntity.ok(new DeleteResponse("Chat session deleted successfully!"));
	}
}

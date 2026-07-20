package com.bodhlens.bodhlens_ai.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bodhlens.bodhlens_ai.dto.ChatRequest;
import com.bodhlens.bodhlens_ai.dto.ChatResponse;
import com.bodhlens.bodhlens_ai.service.ChatService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

	@Autowired
	ChatService chatService;
	
	@PostMapping("/{documentId}")
	public ResponseEntity<ChatResponse> chat(@PathVariable UUID documentId, 
			@RequestBody @Valid ChatRequest request) throws IOException, InterruptedException {
		return ResponseEntity.ok(chatService.chat(documentId, request));
	}
}

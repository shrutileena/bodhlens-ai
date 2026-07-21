package com.bodhlens.bodhlens_ai.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bodhlens.bodhlens_ai.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

	List<ChatMessage> findByChatSessionIdOrderByCreatedAtAsc(UUID chatSessionId);
}

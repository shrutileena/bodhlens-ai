package com.bodhlens.bodhlens_ai.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bodhlens.bodhlens_ai.entity.ChatSession;

public interface ChatSessionRepository extends JpaRepository<ChatSession, UUID> {

	List<ChatSession> findByDocumentIdOrderByUpdatedAtDesc(UUID documentId);
}

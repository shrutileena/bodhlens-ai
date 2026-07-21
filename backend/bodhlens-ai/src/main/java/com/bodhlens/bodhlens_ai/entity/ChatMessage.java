package com.bodhlens.bodhlens_ai.entity;

import com.bodhlens.bodhlens_ai.enums.MessageSender;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
public class ChatMessage extends BaseEntity {

	@Enumerated(EnumType.STRING)
	private MessageSender sender;
	
	private String message;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "chat_session_id")
	@JsonIgnore
	private ChatSession chatSession;
	
}

package com.bodhlens.bodhlens_ai.dto;

import java.time.Instant;

import com.bodhlens.bodhlens_ai.enums.MessageSender;

public record ChatMessageResponse(
		MessageSender sender,
		String message,
		Instant timestamp
		) {

}

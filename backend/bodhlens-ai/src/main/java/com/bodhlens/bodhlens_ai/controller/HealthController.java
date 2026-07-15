package com.bodhlens.bodhlens_ai.controller;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bodhlens.bodhlens_ai.dto.HealthResponse;

@RestController
public class HealthController {

	@GetMapping("/api/health")
	public HealthResponse health(){
		return new HealthResponse(
				"UP", 
				"BodhLens AI", 
				"1.0.0", 
				Instant.now()
				);
	}
}

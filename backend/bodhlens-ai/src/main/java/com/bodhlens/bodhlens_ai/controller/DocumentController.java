package com.bodhlens.bodhlens_ai.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bodhlens.bodhlens_ai.dto.DocumentResponse;
import com.bodhlens.bodhlens_ai.service.DocumentService;

@RestController
@RequestMapping("/api")
public class DocumentController {
	
	@Autowired
	private final DocumentService documentService;
	
	public DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@PostMapping("/documents/upload")
	public ResponseEntity<Map<String, String>> uploadDocuments(@RequestParam("file") MultipartFile file) throws IOException {
		documentService.uploadDocument(file);
		return ResponseEntity.ok(Map.of("message", "File uploaded successfully"));
	}
	
	@GetMapping("/documents")
	public ResponseEntity<List<DocumentResponse>> getDocuments() {
		return ResponseEntity.ok(
				documentService.getDocuments());
	}
}

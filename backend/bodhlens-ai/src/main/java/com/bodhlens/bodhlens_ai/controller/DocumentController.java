package com.bodhlens.bodhlens_ai.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bodhlens.bodhlens_ai.dto.DeleteResponse;
import com.bodhlens.bodhlens_ai.dto.DocumentResponse;
import com.bodhlens.bodhlens_ai.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
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
	
	@GetMapping
	public ResponseEntity<List<DocumentResponse>> getDocuments() {
		return ResponseEntity.ok(
				documentService.getDocuments());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Resource> viewDocument(@PathVariable UUID id) throws IOException {
		Resource resource = documentService.viewDocument(id);
		
		Path path = Paths.get(resource.getFile().getAbsolutePath());
		
		String contentType = Files.probeContentType(path);
		
		if(contentType == null) {
			contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.contentType(MediaType.parseMediaType(contentType))
				.body(resource);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<DeleteResponse> deleteDocument(@PathVariable UUID id) throws IOException {
		return ResponseEntity.ok(documentService.deleteDocument(id));
	}
}

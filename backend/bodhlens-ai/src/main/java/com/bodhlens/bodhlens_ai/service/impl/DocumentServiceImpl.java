package com.bodhlens.bodhlens_ai.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bodhlens.bodhlens_ai.dto.DeleteResponse;
import com.bodhlens.bodhlens_ai.dto.DocumentResponse;
import com.bodhlens.bodhlens_ai.entity.Document;
import com.bodhlens.bodhlens_ai.entity.User;
import com.bodhlens.bodhlens_ai.enums.DocumentStatus;
import com.bodhlens.bodhlens_ai.repository.DocumentRepository;
import com.bodhlens.bodhlens_ai.repository.UserRepository;
import com.bodhlens.bodhlens_ai.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {
	
	private static final String UPLOAD_DIR = "uploads";
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void uploadDocument(MultipartFile file) throws IOException {
		
		Path uploadPath = Paths.get(UPLOAD_DIR);
		
		if(Files.notExists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		
		Path filePath = uploadPath.resolve(uniqueFileName);
		
		file.transferTo(filePath);
		
		// Get logged-in user's email
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		
		Document document = new Document();
		document.setFileName(file.getOriginalFilename());
		document.setFilePath(filePath.toString());
		document.setFileType(file.getContentType());
		document.setFileSize(file.getSize());
		document.setUpdatedAt(Instant.now());
		document.setCreatedAt(Instant.now());
		document.setUser(user);
		document.setStatus(DocumentStatus.UPLOADED);
		
		documentRepository.save(document);
	}

	@Override
	public List<DocumentResponse> getDocuments() {
		List<Document> documents = documentRepository.findAll();
		
		return documents.stream().map(document -> new DocumentResponse(document.getId(), 
				document.getFileName(), document.getStatus(), document.getCreatedAt())
				).toList();
	}

	@Override
	public Resource viewDocument(UUID id) throws MalformedURLException {
		Document document = documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
		String filePath = document.getFilePath();
		Path path = Paths.get(filePath);
		Resource resource = new UrlResource(path.toUri());
		
		if(!resource.exists()) {
			throw new RuntimeException("File not found");
		}
		
		return resource;
	}

	@Override
	public DeleteResponse deleteDocument(UUID id) throws IOException {
		Document document = documentRepository.findById(id).orElseThrow(() -> new RuntimeException("Document not found"));
		String filePath = document.getFilePath();
		Path path = Paths.get(filePath);
		Files.deleteIfExists(path);
		
		documentRepository.delete(document);
		return new DeleteResponse("Document deleted successfully!");
	}

}

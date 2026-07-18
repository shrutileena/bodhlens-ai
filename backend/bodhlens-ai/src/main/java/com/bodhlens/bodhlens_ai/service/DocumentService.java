package com.bodhlens.bodhlens_ai.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bodhlens.bodhlens_ai.dto.DocumentResponse;

public interface DocumentService {
	
	void uploadDocument(MultipartFile file) throws IOException;

	List<DocumentResponse> getDocuments();
}

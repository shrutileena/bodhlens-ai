package com.bodhlens.bodhlens_ai.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.bodhlens.bodhlens_ai.dto.DeleteResponse;
import com.bodhlens.bodhlens_ai.dto.DocumentResponse;

public interface DocumentService {
	
	void uploadDocument(MultipartFile file) throws IOException;

	List<DocumentResponse> getDocuments();

	Resource viewDocument(UUID id) throws MalformedURLException;

	DeleteResponse deleteDocument(UUID id) throws IOException;
}

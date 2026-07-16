package com.bodhlens.bodhlens_ai.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
	
	void uploadDocument(MultipartFile file) throws IOException;
}

package com.bodhlens.bodhlens_ai.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bodhlens.bodhlens_ai.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {
	
	private static final String UPLOAD_DIR = "uploads";

	@Override
	public void uploadDocument(MultipartFile file) throws IOException {
		
		Path uploadPath = Paths.get(UPLOAD_DIR);
		
		if(Files.notExists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		Path filePath = uploadPath.resolve(file.getOriginalFilename());
		
		file.transferTo(filePath);
		
		System.out.println("filepath - " + filePath);
	}

}

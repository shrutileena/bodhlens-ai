package com.bodhlens.bodhlens_ai.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bodhlens.bodhlens_ai.entity.Document;
import com.bodhlens.bodhlens_ai.repository.DocumentRepository;

@Service
public class DocumentTextExtractor {
	
	@Autowired
	DocumentRepository documentRepository;

	public String extractText(UUID documentId) throws IOException {
		
		Document document = documentRepository.findById(documentId).orElseThrow(() -> new RuntimeException("Document not found"));
		Path path = Paths.get(document.getFilePath());
		String fileName = document.getFileName().toLowerCase();
		
		if(fileName.endsWith(".pdf")) {
			try(PDDocument pdfDocument = Loader.loadPDF(path.toFile())) {
				PDFTextStripper stripper = new PDFTextStripper();
				return stripper.getText(pdfDocument);
			}
		}
		if(fileName.endsWith(".docx")) {
			try(XWPFDocument docx = new XWPFDocument(Files.newInputStream(path));
					XWPFWordExtractor extractor = new XWPFWordExtractor(docx)) {
				return extractor.getText();
			}
		}
		if(fileName.endsWith(".txt")) {
			return Files.readString(path);
		}
		throw new IllegalArgumentException("Unsupported document type");
	}
}

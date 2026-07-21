package com.bodhlens.bodhlens_ai.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.bodhlens.bodhlens_ai.ai.dto.OllamaRequest;
import com.bodhlens.bodhlens_ai.ai.dto.OllamaResponse;

@Service
public class OllamaService implements LLMService {

	private final RestClient restClient;

	@Value("${ollama.url}")
	private String ollamaUrl;

	@Value("${ollama.model}")
	private String model;

	public OllamaService(RestClient restClient) {
		this.restClient = restClient;
	}

	@Override
	public String askQuestion(LLMRequest request) {
		String prompt = """
				You are a helpful AI assistant.

				Document:
				%s

				Question:
				%s

				Answer based on only the document.
				""".formatted(request.context(), request.question());

		OllamaRequest ollamaRequest = new OllamaRequest(model, prompt, false);

		OllamaResponse response = restClient.post().uri(ollamaUrl + "/api/generate").body(ollamaRequest).retrieve()
				.body(OllamaResponse.class);

		return response != null ? response.response() : "No response from Ollama.";
	}

	@Override
	public String generateTitle(String question) {
		String prompt = """
				Generate a short title (maximum 4 words) for this conversation.

				Return ONLY the title.

				Question:
				%s
				""".formatted(question);

		OllamaRequest ollamaRequest = new OllamaRequest(model, prompt, false);

		OllamaResponse response = restClient.post().uri(ollamaUrl + "/api/generate").body(ollamaRequest).retrieve()
				.body(OllamaResponse.class);
		return response.response().replace("\"", "").replace(".", "").trim();
	}

}

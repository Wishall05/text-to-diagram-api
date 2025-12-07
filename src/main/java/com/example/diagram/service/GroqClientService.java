package com.example.diagram.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;

@Service
public class GroqClientService {

    @Value("${groq.api.key:}")
    private String apiKey;

    // Groq OpenAI-compatible endpoint
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    private final OkHttpClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public GroqClientService() {
        this.client = new OkHttpClient.Builder()
                .callTimeout(Duration.ofSeconds(30))
                .build();
    }

    public JsonNode callGroq(String systemPrompt, String userPrompt) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Missing groq.api.key in application properties");
        }
        try {
            ObjectMapper m = mapper;
            JsonNode root = m.createObjectNode()
                    .put("model", "openai/gpt-oss-20b")
                    .set("messages", m.createArrayNode()
                            .add(m.createObjectNode().put("role", "system").put("content", systemPrompt))
                            .add(m.createObjectNode().put("role", "user").put("content", userPrompt))
                    );

            String json = m.writeValueAsString(root);

            RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(API_URL)
                    .header("Authorization", "Bearer " + apiKey)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String respBody = response.body() != null ? response.body().string() : "";
                    throw new RuntimeException("Groq API error: " + response.code() + " - " + respBody);
                }
                String responseStr = response.body() != null ? response.body().string() : "";
                return mapper.readTree(responseStr);
            }

        } catch (IOException e) {
            throw new RuntimeException("Groq call failed: " + e.getMessage(), e);
        }
    }
}

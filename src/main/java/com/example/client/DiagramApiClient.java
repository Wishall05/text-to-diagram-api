package com.example.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class DiagramApiClient {

    private static final String API_URL = "http://localhost:8080/api/diagram/generate";
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        // The natural language system description
        String inputText = "A load balancer routes traffic to two backend services which read from MongoDB.";

        // Prepare JSON body
        String jsonBody = mapper.createObjectNode()
                .put("text", inputText)
                .toString();

        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .build();

        System.out.println("\n=== Calling Diagram Generator API ===\n");

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                System.out.println("❌ API Error: " + response.code());
                System.out.println(response.body().string());
                return;
            }

            String responseStr = response.body().string();
            JsonNode root = mapper.readTree(responseStr);

            System.out.println("=== Raw JSON Response ===");
            System.out.println(responseStr);

            System.out.println("\n=== Mermaid ===");
            System.out.println(root.path("mermaid").asText());

            System.out.println("\n=== PlantUML ===");
            System.out.println(root.path("plantuml").asText());

            System.out.println("\n=== Nodes ===");
            for (JsonNode n : root.path("graph").path("nodes")) {
                System.out.println("- " + n.toString());
            }

            System.out.println("\n=== Edges ===");
            for (JsonNode e : root.path("graph").path("edges")) {
                System.out.println("- " + e.toString());
            }

            System.out.println("\n=== Done ===");
        }
    }
}

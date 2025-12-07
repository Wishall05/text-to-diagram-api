package com.example.diagram.service;

import com.example.diagram.model.Edge;
import com.example.diagram.model.Graph;
import com.example.diagram.model.Node;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class LLMParserService {

    private final GroqClientService groq;
    private final ObjectMapper mapper = new ObjectMapper();

    public LLMParserService(GroqClientService groq) {
        this.groq = groq;
    }

    public ParsedResult parse(String text) {
        JsonNode response = groq.callGroq(
                DiagramGenerationPrompt.systemPrompt(),
                text
        );

        // Extract the content
        JsonNode choice = response.path("choices").path(0).path("message").path("content");
        String content = choice.isMissingNode() ? response.toString() : choice.asText();

        // Parse JSON content
        JsonNode root;
        try {
            root = mapper.readTree(content);
        } catch (Exception ex) {
            throw new RuntimeException("LLM produced invalid JSON: " + ex.getMessage() + "\nRaw: " + content);
        }

        ParsedResult result = new ParsedResult();
        result.graph = new Graph();

        JsonNode nodes = root.path("nodes");
        if (nodes.isArray()) {
            for (JsonNode n : nodes) {
                String id = n.path("id").asText();
                String type = n.path("type").asText(null);
                String label = n.path("label").asText(id);
                result.graph.addNode(new Node(id, type, label));
            }
        }

        JsonNode edges = root.path("edges");
        if (edges.isArray()) {
            for (JsonNode e : edges) {
                String from = e.path("from").asText();
                String to = e.path("to").asText();
                String rel = e.path("relation").asText(null);
                result.graph.addEdge(new Edge(from, to, rel));
            }
        }

        result.mermaid = root.path("mermaid").asText(null);
        result.plantuml = root.path("plantuml").asText(null);

        return result;
    }

    public static class ParsedResult {
        public Graph graph;
        public String mermaid;
        public String plantuml;
    }
}

package com.example.diagram.service;

import com.example.diagram.model.DiagramResponse;
import org.springframework.stereotype.Service;

@Service
public class DiagramService {

    private final LLMParserService llm;

    public DiagramService(LLMParserService llm) {
        this.llm = llm;
    }

    public DiagramResponse generateDiagram(String text) {
        var parsed = llm.parse(text);

        DiagramResponse resp = new DiagramResponse();
        resp.setGraph(parsed.graph);
        resp.setMermaid(parsed.mermaid);
        resp.setPlantuml(parsed.plantuml);
        return resp;
    }
}

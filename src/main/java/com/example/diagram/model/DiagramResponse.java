package com.example.diagram.model;

public class DiagramResponse {
    private String mermaid;
    private String plantuml;
    private Graph graph;

    public DiagramResponse() {}

    public String getMermaid() { return mermaid; }
    public void setMermaid(String mermaid) { this.mermaid = mermaid; }

    public String getPlantuml() { return plantuml; }
    public void setPlantuml(String plantuml) { this.plantuml = plantuml; }

    public Graph getGraph() { return graph; }
    public void setGraph(Graph graph) { this.graph = graph; }
}

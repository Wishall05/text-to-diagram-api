package com.example.diagram.model;

public class Edge {
    private String from;
    private String to;
    private String relation;

    public Edge() {}
    public Edge(String from, String to, String relation) {
        this.from = from; this.to = to; this.relation = relation;
    }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getRelation() { return relation; }
    public void setRelation(String relation) { this.relation = relation; }
}

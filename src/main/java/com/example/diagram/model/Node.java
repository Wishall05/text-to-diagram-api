package com.example.diagram.model;

public class Node {
    private String id;
    private String type;
    private String label;

    public Node() {}
    public Node(String id, String type, String label) {
        this.id = id;
        this.type = type;
        this.label = label;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
}

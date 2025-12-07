package com.example.diagram.model;

public class InputRequest {
    private String text;

    public InputRequest() {}
    public InputRequest(String t) { this.text = t; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}

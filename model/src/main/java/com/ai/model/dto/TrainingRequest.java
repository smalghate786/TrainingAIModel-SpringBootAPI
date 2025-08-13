package com.ai.model.dto;


public class TrainingRequest {
    private String text;
    private int label; // 0 = negative, 1 = positive

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public int getLabel() { return label; }
    public void setLabel(int label) { this.label = label; }
}


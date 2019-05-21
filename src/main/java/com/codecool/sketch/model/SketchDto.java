package com.codecool.sketch.model;

public class SketchDto {
    private Sketch data;
    private String content;

    public SketchDto(Sketch data, String content) {
        this.data = data;
        this.content = content;
    }

    public Sketch getData() {
        return data;
    }

    public String getContent() {
        return content;
    }
}

package com.codecool.sketch.model;

public class Sketch extends EmptySketchData implements Item{
    private String content;

    public Sketch(int id, int foldersId, String name, String content) {
        super(id, foldersId, name);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}

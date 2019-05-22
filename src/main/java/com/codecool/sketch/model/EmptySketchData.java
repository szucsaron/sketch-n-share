package com.codecool.sketch.model;

public class EmptySketchData implements Item{
    protected int id;
    protected String name;
    protected int foldersId;

    public EmptySketchData(int id, int foldersId, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

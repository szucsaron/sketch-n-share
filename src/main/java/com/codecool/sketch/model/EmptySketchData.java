package com.codecool.sketch.model;

public class EmptySketchData implements Item{
    protected int id;
    protected String name;
    protected int folderId;

    public EmptySketchData(int id, int folderId, String name) {
        this.id = id;
        this.folderId = folderId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getFolderId() {
        return folderId;
    }
}

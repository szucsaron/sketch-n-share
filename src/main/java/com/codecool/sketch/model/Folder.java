package com.codecool.sketch.model;


public class Folder implements Item{
    private int id;
    private String name;
    private String owner;

    public Folder(int id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }
}

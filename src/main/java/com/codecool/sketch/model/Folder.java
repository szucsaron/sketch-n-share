package com.codecool.sketch.model;


public class Folder {
    private int id;
    private String name;
    private int owner;

    public Folder(int id, String name, int owner) {
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

    public int getOwner() {
        return owner;
    }
}

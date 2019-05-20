package com.codecool.sketch.model;

public class User {
    private int id;
    private String name;
    private String password;
    private Role role;

    public User(int id, String name, String password, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    public Role getRole() {
        return role;
    }
}

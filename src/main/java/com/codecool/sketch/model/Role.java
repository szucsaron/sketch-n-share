package com.codecool.sketch.model;


public enum Role {
    REGULAR, ADMIN;
    public static int getIntVal(Role role) {
        if (role.equals(Role.ADMIN)) {
            return 1;
        } else if (role.equals(Role.REGULAR)){
            return 0;
        }
        throw new IllegalArgumentException();
    }
}

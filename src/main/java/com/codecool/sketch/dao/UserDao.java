package com.codecool.sketch.dao;

import com.codecool.sketch.model.User;

import java.sql.SQLException;

public interface UserDao {
    User fetchByName(String name) throws SQLException;
}

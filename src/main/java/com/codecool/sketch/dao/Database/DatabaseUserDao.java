package com.codecool.sketch.dao.Database;

import com.codecool.sketch.dao.UserDao;
import com.codecool.sketch.model.User;

import java.sql.Connection;

public class DatabaseUserDao extends DatabaseAbstractDao implements UserDao {
    public DatabaseUserDao(Connection connection) {
        super(connection);
    }


}

package com.codecool.sketch.dao.database;

import com.codecool.sketch.dao.UserDao;
import com.codecool.sketch.model.Role;
import com.codecool.sketch.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUserDao extends DatabaseAbstractDao implements UserDao {
    public DatabaseUserDao(Connection connection) {
        super(connection);
    }

    public User fetchByName(String name) throws SQLException {
        String sql = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                return fetchUser(rs);
            } else {
                return null;
            }
        }
    }

    private User fetchUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String password = resultSet.getString("password");
        int roleInt = resultSet.getInt("role");
        Role role;
        switch (roleInt) {
            case 0:
                role = Role.REGULAR;
                break;
            case 1:
                role = Role.ADMIN;
                break;
            default:
                throw new SQLException("Can't parse role value");
        }
        return new User(id, name, password, role);
    }
}

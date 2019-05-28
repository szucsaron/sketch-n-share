package com.codecool.sketch.dao.database;

import com.codecool.sketch.dao.UserDao;
import com.codecool.sketch.model.Role;
import com.codecool.sketch.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUserDao extends DatabaseAbstractDao implements UserDao {
    public DatabaseUserDao(Connection connection) {
        super(connection);
    }

    public User fetchByName(String name) throws SQLException {
        String sql = "SELECT * FROM users WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            return executeSingleFetch(preparedStatement);
        }
    }

    public List<User> fetchBySharedFolder(int ownerId, int folderId) throws SQLException {
        String sql = "SELECT users.name, users.id, users.role  FROM users\n" +
                "RIGHT JOIN shares ON users.id = users_id\n" +
                "LEFT JOIN folders ON shares.folders_id = folders.id\n" +
                "WHERE owner = ? AND folders_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, ownerId);
            preparedStatement.setInt(2, folderId);
            return executeMultipleFetch(preparedStatement);
        }
    }


    public List<User> fetchBySharedFolder(int folderId) throws SQLException {
        String sql = "SELECT users.*  FROM users\n" +
                "RIGHT JOIN shares ON users.id = users_id\n" +
                "WHERE folders_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, folderId);
            return executeMultipleFetch(preparedStatement);
        }
    }

    public void shareFolderWithUser(int owner, String userName, int folderId) throws SQLException {
        //         String sql = "INSERT INTO shares (users_id, folders_id) VALUES (?, ?)";
        // -- params: owner, folder id, user id
        String sql = "SELECT \"share_folder_with_user\"(?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, owner);
            preparedStatement.setInt(2, folderId);
            preparedStatement.setString(3, userName);
            preparedStatement.execute();
        }
    }


    public void unshareFolderWithUser(int owner, int userId, int folderId) throws SQLException {
        String sql = "SELECT \"unshare_folder_with_user\" (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, owner);
            preparedStatement.setInt(2, folderId);
            preparedStatement.setInt(3, userId);
            preparedStatement.execute();
        }
    }

    // private

    private User executeSingleFetch(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
        ResultSet rs = preparedStatement.getResultSet();
        List<User> users = new ArrayList<>();
        if (rs.next()) {
            return fetchUser(rs);
        } else {
            return null;
        }
    }

    private List<User> executeMultipleFetch(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
        ResultSet rs = preparedStatement.getResultSet();
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(fetchUser(rs));
        }
        return users;
    }

    private User fetchUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String password;
        try {
            password = resultSet.getString("password");
        } catch (SQLException e) {
            password = null;
        }
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

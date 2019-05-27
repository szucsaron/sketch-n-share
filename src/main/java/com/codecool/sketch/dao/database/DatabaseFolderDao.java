package com.codecool.sketch.dao.database;

import com.codecool.sketch.dao.FolderDao;
import com.codecool.sketch.model.Folder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFolderDao extends DatabaseAbstractDao implements FolderDao {
    public DatabaseFolderDao(Connection connection) {
        super(connection);
    }

    public List<Folder> fetchByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM folders WHERE owner = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.execute();
            return executeMultipleFetch(preparedStatement);
        }
    }

    public List<Folder> fetchAll() throws SQLException {
        String sql = "SELECT * FROM folders";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
            return executeMultipleFetch(preparedStatement);
        }
    }

    public void createNew(String name, int userId) throws SQLException {
        String sql = "INSERT INTO folders (name, owner) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        }
    }

    public void rename(int userId, int folderId, String name) throws SQLException {
        String sql = "UPDATE folders SET name = ? WHERE owner = ? AND id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, folderId);
            preparedStatement.executeUpdate();
        }
    }

    public void rename(int folderId, String name) throws SQLException {
        String sql = "UPDATE folders SET name = ? AND id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, folderId);
            preparedStatement.executeUpdate();
        }
    }

    public void delete(int userId, int folderId) throws SQLException {
            String sql = "DELETE FROM folders WHERE owner = ? AND id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, folderId);
                preparedStatement.executeUpdate();
            }
    }

    public void delete(int folderId) throws SQLException {
        String sql = "DELETE FROM folders WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, folderId);
            preparedStatement.executeUpdate();
        }
    }

    private List<Folder> executeMultipleFetch(PreparedStatement preparedStatement) throws SQLException{
        preparedStatement.execute();
        ResultSet rs = preparedStatement.getResultSet();
        List<Folder> folders = new ArrayList<>();
        while (rs.next()) {
            folders.add(fetchFolder(rs));
        }
        return folders;
    }

    private Folder fetchFolder(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int owner = rs.getInt("owner");
        return new Folder(id, name, owner);
    }
}

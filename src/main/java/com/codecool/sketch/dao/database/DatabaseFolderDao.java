package com.codecool.sketch.dao.database;

import com.codecool.sketch.dao.FolderDao;
import com.codecool.sketch.dto.FolderOwnerDto;
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
        String sql = "SELECT folders.*, users.name AS owner_name FROM folders " +
                "LEFT JOIN users ON users.id = owner " +
                "WHERE owner = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.execute();
            return executeMultipleFetch(preparedStatement);
        }
    }

    public List<Folder> fetchAllShared(int userId) throws SQLException {
        String sql = "SELECT folders.*, users.name AS owner_name FROM folders " +
                "LEFT JOIN shares ON folders.id = folders_id " +
                "LEFT JOIN users ON users.id = owner " +
                "WHERE users_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.execute();
            return executeMultipleFetch(preparedStatement);
        }
    }


    public List<Folder> fetchAll() throws SQLException {
        String sql = "SELECT folders.*, users.name AS owner_name FROM folders\n" +
                "LEFT JOIN users ON users.id = folders.owner";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
            return executeMultipleFetch(preparedStatement);
        }
    }

    public List<FolderOwnerDto> fetchAllOwnedDtos() throws SQLException {
        String sql = "SELECT folders.*, users.name AS owner_name FROM folders\n" +
                "LEFT JOIN users ON users.id = folders.owner";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            List<FolderOwnerDto> folderOwnerDtos = new ArrayList<>();
            while (rs.next()) {
                Folder folder = fetchFolder(rs);
                String owner = rs.getString("owner_name");
                folderOwnerDtos.add(new FolderOwnerDto(folder, owner));
            }
            return folderOwnerDtos;
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
        String sql = "UPDATE folders SET name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, folderId);
            preparedStatement.executeUpdate();
        }
    }

    public void changeOwner(int folderId, String ownerName) throws SQLException {
        String sql = "SELECT \"change_folder_owner\" (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, folderId);
            preparedStatement.setString(2, ownerName);
            preparedStatement.execute();
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

    private List<Folder> executeMultipleFetch(PreparedStatement preparedStatement) throws SQLException {
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
        String owner = rs.getString("owner_name");
        return new Folder(id, name, owner);
    }
}

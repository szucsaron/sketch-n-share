package com.codecool.sketch.dao.database;

import com.codecool.sketch.dao.SketchDao;
import com.codecool.sketch.model.EmptySketchData;
import com.codecool.sketch.model.Sketch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSketchDao extends DatabaseAbstractDao implements SketchDao {

    public DatabaseSketchDao(Connection connection) {
        super(connection);
    }

    public List<EmptySketchData> findByFolderId(int userId, int folderId) throws SQLException {
        String sql = "SELECT sketches.name, sketches.id, sketches.folders_id FROM sketches \n" +
                "LEFT JOIN folders ON folders_id = folders.id \n" +
                "WHERE owner = ? AND folders_id = ?" +
                "ORDER BY sketches.name";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, folderId);
            return executeMultipleFetch(preparedStatement);
        }
    }

    public List<EmptySketchData> findByFolderId(int folderId) throws SQLException {
        String sql = "SELECT name, id, folders_id FROM sketches WHERE folders_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, folderId);
            return executeMultipleFetch(preparedStatement);
        }
    }

    public List<EmptySketchData> findBySharedFolderId(int userId, int folderId) throws SQLException {
        String sql = "SELECT sketches.name, sketches.id, sketches.folders_id FROM sketches\n" +
                "LEFT JOIN folders ON sketches.folders_id = folders.id\n" +
                "LEFT JOIN shares ON folders.id = shares.folders_id\n" +
                "WHERE folders.id = ? AND shares.users_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, folderId);
            preparedStatement.setInt(2, userId);
            return executeMultipleFetch(preparedStatement);
        }
    }


    public Sketch findById(int userId, int id) throws SQLException {
        String sql = "SELECT sketches.* FROM sketches \n" +
                "LEFT JOIN folders ON folders_id = folders.id \n" +
                "WHERE owner = ? AND sketches.id = ?" +
                "ORDER BY sketches.name";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, id);
            return executeSingle(preparedStatement);
        }
    }

    public Sketch findById(int id) throws SQLException {
        String sql = "SELECT * FROM sketches WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            return executeSingle(preparedStatement);
        }
    }

    public void update(int userId, int id, int folderId, String name, String content) throws SQLException {
        String sql = "SELECT \"update_sketch\" (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, folderId);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, content);
            preparedStatement.execute();
        }
    }

    public void update(int id, int folderId, String name, String content) throws SQLException {
        String sql = "UPDATE sketches SET name = ?, folders_id = ?, content = ?\n" +
                "WHERE sketches.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, folderId);
            preparedStatement.setString(3, content);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        }
    }


    public void updateHeader(int userId, int id, int folderId, String name) throws SQLException {
        String sql = "UPDATE sketches SET name = ?, folders_id = ?\n" +
                "LEFT JOIN folders ON folders_id = folders.id \n" +
                "WHERE owner = ? AND sketches.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, folderId);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        }
    }

    public void create(int userId, int folderId, String name) throws SQLException {
        String sql = "SELECT \"create_sketch\" (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, folderId);
            preparedStatement.setString(3, name);
            preparedStatement.execute();
        }
    }

    public void create(int folderId, String name) throws SQLException {
        String sql = "INSERT INTO sketches (folders_id, name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, folderId);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        }
    }

    public void rename(int userId, int id, String name) throws SQLException {
        String sql = "SELECT \"rename_sketch\" (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, name);
            preparedStatement.execute();
        }
    }

    public void rename(int id, String name) throws SQLException {
        String sql = "UPDATE sketches SET name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }


    // Utility methods

    private Sketch executeSingle(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
        ResultSet rs = preparedStatement.getResultSet();
        if (rs.next()) {
            return fetchSketch(rs);
        } else {
            return null;
        }
    }


    private List<EmptySketchData> executeMultipleFetch(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.execute();
        ResultSet rs = preparedStatement.getResultSet();
        List<EmptySketchData> esd = new ArrayList<>();
        while (rs.next()) {
            esd.add(fetchEmptySketchData(rs));
        }
        return esd;
    }

    private EmptySketchData fetchEmptySketchData(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int foldersId = resultSet.getInt("folders_id");
        String name = resultSet.getString("name");
        return new EmptySketchData(id, foldersId, name);
    }

    private Sketch fetchSketch(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int foldersId = resultSet.getInt("folders_id");
        String name = resultSet.getString("name");
        String content = resultSet.getString("content");

        return new Sketch(id, foldersId, name, content);
    }


}

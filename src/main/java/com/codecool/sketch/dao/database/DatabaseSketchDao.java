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

    public List<EmptySketchData> findByFolderId(int userId, int folderId) throws SQLException{
        String sql = "SELECT sketches.name, sketches.id, sketches.folders_id FROM sketches \n" +
                "LEFT JOIN folders ON folders_id = folders.id \n" +
                "WHERE owner = ? AND folders_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, folderId);
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            List<EmptySketchData> esd = new ArrayList<>();
            while (rs.next()) {
                esd.add(fetchEmptySketchData(rs));
            }
            return esd;
        }
    }

    public Sketch findById(int userId, int id) throws SQLException{
        String sql = "SELECT sketches.* FROM sketches \n" +
                "LEFT JOIN folders ON folders_id = folders.id \n" +
                "WHERE owner = ? AND sketches.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            if (rs.next()) {
                return fetchSketch(rs);
            } else {
                return null;
            }
        }
    }


    private EmptySketchData fetchEmptySketchData(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        int foldersId = resultSet.getInt("folders_id");
        String name = resultSet.getString("name");
        return new EmptySketchData(id, foldersId, name);
    }

    private Sketch fetchSketch(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        int foldersId = resultSet.getInt("folders_id");
        String name = resultSet.getString("name");
        String content = resultSet.getString("content");

        return new Sketch(id, foldersId, name, content);
    }
}

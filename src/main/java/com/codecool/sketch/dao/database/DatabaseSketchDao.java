package com.codecool.sketch.dao.database;

import com.codecool.sketch.dao.SketchDao;
import com.codecool.sketch.model.EmptySketchData;

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

    private EmptySketchData fetchEmptySketchData(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        int foldersId = resultSet.getInt("folders_id");
        String name = resultSet.getString("name");
        return new EmptySketchData(id, foldersId, name);
    }
}

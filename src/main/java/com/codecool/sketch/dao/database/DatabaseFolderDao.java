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
            ResultSet rs = preparedStatement.getResultSet();
            List<Folder> folders = new ArrayList<>();
            while (rs.next()) {
                folders.add(fetchFolder(rs));
            }
            return folders;
        }
    }

    private Folder fetchFolder(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int owner = rs.getInt("owner");
        return new Folder(id, name, owner);
    }
}

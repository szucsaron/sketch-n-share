package com.codecool.sketch.dao;

import com.codecool.sketch.model.EmptySketchData;

import java.sql.SQLException;
import java.util.List;

public interface SketchDao {
    public List<EmptySketchData> findByFolderId(int userId, int folderId) throws SQLException;
}

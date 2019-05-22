package com.codecool.sketch.dao;

import com.codecool.sketch.model.EmptySketchData;
import com.codecool.sketch.model.Sketch;

import java.sql.SQLException;
import java.util.List;

public interface SketchDao {
    List<EmptySketchData> findByFolderId(int userId, int folderId) throws SQLException;

    Sketch findById(int userId, int id) throws SQLException;
}

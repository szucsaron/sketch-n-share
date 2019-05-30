package com.codecool.sketch.dao;

import com.codecool.sketch.model.EmptySketchData;
import com.codecool.sketch.model.Sketch;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface SketchDao {
    List<EmptySketchData> findByFolderId(int userId, int folderId) throws SQLException;

    List<EmptySketchData> findBySharedFolderId(int userId, int folderId) throws SQLException;

    List<EmptySketchData> findByFolderId(int folderId) throws SQLException;

    Sketch findById(int userId, int id) throws SQLException;

    Sketch findById(int id) throws SQLException;

    Sketch findSharedById(int userId, int id) throws SQLException;

    void update(int userId, int id, int folderId, String name, String content) throws SQLException;

    void update(int id, int folderId, String name, String content) throws SQLException;

    void create(int userId, int folderId, String name) throws SQLException;

    void create(int folderId, String name) throws SQLException;

    void rename(int userId, int id, String name) throws SQLException;

    void rename(int id, String name) throws SQLException;

    void delete(int userId, int id) throws SQLException;

    void delete(int id) throws SQLException;
}

package com.codecool.sketch.dao;

import com.codecool.sketch.model.Folder;

import java.sql.SQLException;
import java.util.List;

public interface FolderDao {
    List<Folder> fetchByUserId(int userId) throws SQLException;

    void createNew(String name, int userId) throws SQLException;

    void rename(int userId, int folderId, String name) throws SQLException;

    void rename(int folderId, String name) throws SQLException;

    void delete(int userId, int folderId) throws SQLException;

    void delete(int folderId) throws SQLException;
}

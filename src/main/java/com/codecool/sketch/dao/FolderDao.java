package com.codecool.sketch.dao;

import com.codecool.sketch.model.Folder;

import java.sql.SQLException;
import java.util.List;

public interface FolderDao {
    List<Folder> fetchByUserId(int userId) throws SQLException;
}

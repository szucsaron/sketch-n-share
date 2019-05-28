package com.codecool.sketch.dao;

import com.codecool.sketch.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    User fetchByName(String name) throws SQLException;

    List<User> fetchBySharedFolder(int folderId) throws SQLException;

    List<User> fetchBySharedFolder(int ownerId, int folderId) throws SQLException;


    void shareFolderWithUser(int owner, int userId, int folderId) throws SQLException;

    void removeFolderShare(int owner, int userId, int folderId) throws SQLException;
}

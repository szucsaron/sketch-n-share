package com.codecool.sketch.dao;

import com.codecool.sketch.model.Role;
import com.codecool.sketch.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {


    User fetchByName(String name) throws SQLException;

    User fetchById(int id) throws SQLException;

    List<User> featchAll() throws SQLException;

    List<User> fetchBySharedFolder(int folderId) throws SQLException;

    List<User> fetchBySharedFolder(int ownerId, int folderId) throws SQLException;

    void shareFolderWithUser(int owner, String userName, int folderId) throws SQLException;

    void unshareFolderWithUser(int owner, int userId, int folderId) throws SQLException;

    void add(String name, String password, int role) throws SQLException;

    void delete(int id) throws SQLException;

    void modify(int userId, String name, String password, int role) throws SQLException;
}

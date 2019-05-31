package com.codecool.sketch.service;


import com.codecool.sketch.model.User;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface UserService extends AbstractService {


    List<User> fetchAll() throws SQLException, ServiceException;

    User fetchById(String id) throws SQLException, ServiceException;

    List<User> fetchBySharedFolder(String folderId) throws SQLException, ServiceException;

    void shareFolderWithUser(String userName, String folderId) throws SQLException, ServiceException;

    void unshareFolderWithUser(String userId, String folderId) throws SQLException, ServiceException;

    void add(String name, String password, String role) throws SQLException, ServiceException;

    void delete(String id) throws SQLException, ServiceException;

    void modify(String userId, String name, String password, String role) throws SQLException, ServiceException;

}

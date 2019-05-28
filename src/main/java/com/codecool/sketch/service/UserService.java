package com.codecool.sketch.service;


import com.codecool.sketch.model.User;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface UserService  extends AbstractService {

    void addUser(String name, String password, String email, String role) throws SQLException, ServiceException;

    List<User> fetchBySharedFolder(String folderId) throws SQLException, ServiceException;

    void shareFolderWithUser(String userName, String folderId) throws SQLException, ServiceException;

    void unshareFolderWithUser(String userId, String folderId) throws SQLException, ServiceException;

}

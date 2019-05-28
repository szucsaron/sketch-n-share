package com.codecool.sketch.service.impl;

import com.codecool.sketch.dao.UserDao;
import com.codecool.sketch.model.User;

import java.sql.SQLException;
import java.util.List;

import com.codecool.sketch.service.UserService;
import com.codecool.sketch.service.exception.ServiceException;



public final class ImplUserService extends ImplAbstractService implements UserService {

    private final UserDao userDao;

    public ImplUserService(User user, UserDao userDao) {
        super(user);
        this.userDao = userDao;
    }

    public ImplUserService(UserDao userDao) {
        super(null);
        this.userDao = userDao;
    }


    public List<User> fetchBySharedFolder(String folderId) throws SQLException, ServiceException {
        if (adminMode) {
            return null;
        } else {
            return userDao.fetchBySharedFolder(fetchUserId(), fetchInt(folderId, "folderId"));
        }
    }

    public void addUser(String name, String password, String email, String role) throws SQLException, ServiceException {

    }

    public void shareFolderWithUser(String userName, String folderId) throws SQLException, ServiceException{
        User user = userDao.fetchByName(userName);
        if (user == null) {
            throw new ServiceException("User doesn't exist!");
        }
        userDao.shareFolderWithUser(fetchUserId(), user.getId(), fetchInt(folderId, "folderId"));
    }

    public void unshareFolderWithUser(String userName, String folderId) throws SQLException, ServiceException {
        
    }



}

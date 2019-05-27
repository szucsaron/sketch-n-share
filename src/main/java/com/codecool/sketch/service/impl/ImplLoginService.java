package com.codecool.sketch.service.impl;

import com.codecool.sketch.dao.UserDao;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.LoginService;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;

public class ImplLoginService implements LoginService {
    private UserDao userDao;

    public ImplLoginService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User fetch(String name, String password) throws SQLException, ServiceException {
        try {
            User user = userDao.fetchByName(name);
            if (user == null || !user.getPassword().equals(password)) {
                throw new ServiceException("Bad login");
            }
            return user;
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}

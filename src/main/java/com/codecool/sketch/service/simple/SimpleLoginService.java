package com.codecool.sketch.service.simple;

import com.codecool.sketch.dao.UserDao;
import com.codecool.sketch.model.User;

import java.sql.SQLException;

import com.codecool.sketch.dao.UserDao;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.LoginService;
import com.codecool.sketch.service.exception.ServiceException;
//import javafx.concurrent.Service;
import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;

public final class SimpleLoginService implements LoginService {

    private final UserDao userDao;

    public SimpleLoginService(UserDao userDao) {
        this.userDao = userDao;
    }


    public User loginUser(String name, String password) throws SQLException, ServiceException {
        try {
            User user = userDao.findByName(name);
            if (user == null || !user.getPassword().equals(password)) {
                throw new ServiceException("Bad login");
            }
            return user;
        } catch (IllegalArgumentException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}


package com.codecool.sketch.service;


import com.codecool.sketch.model.User;
import com.codecool.sketch.service.exception.ServiceException;


import java.sql.SQLException;

public interface LoginService {
    public User fetch(String email, String password) throws SQLException, ServiceException;
}

package com.codecool.sketch.service;


import com.codecool.sketch.model.User;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;

public interface UserService  extends AbstracttService{

    User addUser(String name, String password, String email, String role) throws SQLException, ServiceException;

}

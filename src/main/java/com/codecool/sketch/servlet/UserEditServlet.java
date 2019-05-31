package com.codecool.sketch.servlet;


import com.codecool.sketch.dao.UserDao;
import com.codecool.sketch.dao.database.DatabaseUserDao;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.LoginService;
import com.codecool.sketch.service.UserService;
import com.codecool.sketch.service.exception.ServiceException;
import com.codecool.sketch.service.impl.ImplLoginService;
import com.codecool.sketch.service.impl.ImplUserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/user_edit")
public class UserEditServlet extends AbstractServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new ImplUserService(fetchUser(req), userDao);
            String userId = req.getParameter("user_id");
            userService.validateAdminMode(fetchAdminMode(req));
            User user = userService.fetchById(userId);

            sendMessage(resp, HttpServletResponse.SC_OK, user);

        } catch (SQLException | ServiceException ex) {
            handleError(resp, ex);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new ImplUserService(fetchUser(req), userDao);
            String userId = req.getParameter("user_id");
            String userName = req.getParameter("user_name");
            String password = req.getParameter("password");
            String role = req.getParameter("role");

            userService.validateAdminMode(fetchAdminMode(req));
            userService.modify(userId, userName, password, role);

            sendMessage(resp, HttpServletResponse.SC_OK, "User modified");
        } catch (SQLException | ServiceException ex) {
            handleError(resp, ex);
        }
    }
}

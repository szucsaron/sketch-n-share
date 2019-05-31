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

@WebServlet("/protected/users")
public class UsersServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new ImplUserService(fetchUser(req), userDao);
            userService.validateAdminMode(fetchAdminMode(req));
            List<User> users = userService.fetchAll();

            sendMessage(resp, HttpServletResponse.SC_OK, users);

        } catch (ServiceException | SQLException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
            handleError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new ImplUserService(fetchUser(req), userDao);
            userService.validateAdminMode(fetchAdminMode(req));
            String userName = req.getParameter("user_name");
            String password = req.getParameter("password");
            String role = req.getParameter("role");

            userService.add(userName, password, role);

            sendMessage(resp, HttpServletResponse.SC_OK, "User created");

        } catch (ServiceException | SQLException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
            handleError(resp, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new ImplUserService(fetchUser(req), userDao);
            userService.validateAdminMode(fetchAdminMode(req));
            String id = req.getParameter("user_id");

            userService.delete(id);

            sendMessage(resp, HttpServletResponse.SC_OK, "User deleted");

        } catch (ServiceException | SQLException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
            handleError(resp, ex);
        }
    }
}

package com.codecool.sketch.servlet;



import com.codecool.sketch.dao.database.DatabaseUserDao;
import com.codecool.sketch.dao.UserDao;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.exception.ServiceException;
import com.codecool.sketch.service.simple.SimpleUserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends AbstractServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            UserDao userDao = new DatabaseUserDao(connection);
            SimpleUserService simpleUserService = new SimpleUserService(userDao);

            String email = req.getParameter("name");
            String password = req.getParameter("password");

            User user = simpleUserService.fetch(email, password);
            req.getSession().setAttribute("user", user);

            sendMessage(resp, HttpServletResponse.SC_OK, user);


        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}

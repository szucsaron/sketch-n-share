package com.codecool.sketch.servlet;



import com.codecool.sketch.dao.Database.DatabaseUserDao;
import com.codecool.sketch.dao.UserDao;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.exception.ServiceException;
import com.codecool.sketch.service.simple.SimpleLoginService;

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
            SimpleLoginService simpleLoginService = new SimpleLoginService(userDao);

            String email = req.getParameter("email");
            String password = req.getParameter("password");

            User user = simpleLoginService.loginUser(email, password);
            req.getSession().setAttribute("user", user);

            sendMessage(resp, HttpServletResponse.SC_OK, user);

            throw new ServiceException();
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}

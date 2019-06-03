package com.codecool.sketch.servlet;

import com.codecool.sketch.dto.MessageDto;
import com.codecool.sketch.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

abstract class AbstractServlet extends HttpServlet {
    Connection getConnection(ServletContext sce) throws SQLException {
        DataSource dataSource = (DataSource) sce.getAttribute("dataSource");
        return dataSource.getConnection();
    }

    private final ObjectMapper om = new ObjectMapper();


    protected void sendMessage(HttpServletResponse resp, int status, String message) throws IOException {
        sendMessage(resp, status, new MessageDto(message));
    }

    protected void sendMessage(HttpServletResponse resp, int status, Object object) throws IOException {
        resp.setStatus(status);
        om.writeValue(resp.getOutputStream(), object);

    }

    protected void handleError(HttpServletResponse resp, Exception ex) throws IOException {
        String msg = ex.getLocalizedMessage();
        String[] msgArr = msg.split("Where:");
        sendMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, msgArr[0]);

        ex.printStackTrace();
    }

    protected User fetchUser(HttpServletRequest req) {
        User loggedInUser = (User) req.getSession().getAttribute("user");
        return loggedInUser;
    }

    protected void removeUser(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
    }

    protected String fetchAdminMode(HttpServletRequest req) {
        return req.getParameter("admin_mode");
    }
}

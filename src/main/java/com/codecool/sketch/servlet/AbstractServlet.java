package com.codecool.sketch.servlet;

import com.codecool.sketch.dto.MessageDto;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.SketchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;

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
        sendMessage(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        ex.printStackTrace();
    }

    protected User fetchUser(HttpServletRequest req) {
        User loggedInUser = (User) req.getSession().getAttribute("user");
        return loggedInUser;
    }

    protected void validateAdminMode(HttpServletRequest req, SketchService sketchService) {
        String adminMode = req.getParameter("admin_mode");
        sketchService.validateAdminMode(adminMode);
    }
}

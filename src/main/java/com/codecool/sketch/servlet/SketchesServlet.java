package com.codecool.sketch.servlet;

import com.codecool.sketch.dao.SketchDao;
import com.codecool.sketch.dao.database.DatabaseSketchDao;
import com.codecool.sketch.model.EmptySketchData;
import com.codecool.sketch.service.SketchService;
import com.codecool.sketch.service.exception.ServiceException;
import com.codecool.sketch.service.impl.ImplSketchServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/protected/sketches")
public class SketchesServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // List all sketches in a folder
        try (Connection connection = getConnection(getServletContext())) {
            String folderId = req.getParameter("folder_id");
            SketchDao sketchDao = new DatabaseSketchDao(connection);
            SketchService sketchService = new ImplSketchServiceImpl(fetchUser(req), sketchDao);
            sketchService.validateAdminMode(fetchAdminMode(req));
            List<EmptySketchData> emptySketchData = sketchService.fetchEmptiesByFolderId(folderId);
            sendMessage(resp, SC_OK, emptySketchData);
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // create empty sketch
        try (Connection connection = getConnection(getServletContext())) {

            SketchDao sketchDao = new DatabaseSketchDao(connection);
            SketchService sketchService = new ImplSketchServiceImpl(fetchUser(req), sketchDao);
            sketchService.validateAdminMode(fetchAdminMode(req));
            String folderId = req.getParameter("folder_id");
            String sketchName = req.getParameter("name");
            sketchService.create(folderId, sketchName);


            sendMessage(resp, SC_OK, "New sketch created");
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Rename sketch
        try (Connection connection = getConnection(getServletContext())) {
            SketchDao sketchDao = new DatabaseSketchDao(connection);
            SketchService sketchService = new ImplSketchServiceImpl(fetchUser(req), sketchDao);
            sketchService.validateAdminMode(fetchAdminMode(req));
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            sketchService.rename(id, name);

            sendMessage(resp, SC_OK, "Sketch renamed");
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }
}

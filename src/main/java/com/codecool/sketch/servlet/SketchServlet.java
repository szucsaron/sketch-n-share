package com.codecool.sketch.servlet;

import com.codecool.sketch.dao.SketchDao;
import com.codecool.sketch.dao.database.DatabaseSketchDao;
import com.codecool.sketch.model.Sketch;
import com.codecool.sketch.service.SketchService;
import com.codecool.sketch.service.exception.ServiceException;
import com.codecool.sketch.service.impl.ImplSketchServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/protected/sketch")
public class SketchServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // List all sketches in a folder
        try (Connection connection = getConnection(getServletContext())) {
            String sketchId = req.getParameter("sketch_id");
            SketchDao sketchDao = new DatabaseSketchDao(connection);
            SketchService sketchService = new ImplSketchServiceImpl(fetchUser(req), sketchDao);
            Sketch sketch = sketchService.fetchSketchById(sketchId);
            sendMessage(resp, SC_OK, sketch);
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Update sketch
        String a = "3";
        try (Connection connection = getConnection(getServletContext())) {
            String sketchId = req.getParameter("sketch_id");
            String folderId = req.getParameter("folder_id");
            String name = req.getParameter("name");
            String content = req.getParameter("content");

            SketchDao sketchDao = new DatabaseSketchDao(connection);
            SketchService sketchService = new ImplSketchServiceImpl(fetchUser(req), sketchDao);
            validateAdminMode(req, sketchService);
            sketchService.update(sketchId, folderId, name, content);

            sendMessage(resp, SC_OK, "Sketch updated");
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }
}
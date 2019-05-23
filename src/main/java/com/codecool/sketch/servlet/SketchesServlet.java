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
            List<EmptySketchData> emptySketchData = sketchService.fetchEmptiesByFolderId(folderId);
            sendMessage(resp, SC_OK, emptySketchData);
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }
}

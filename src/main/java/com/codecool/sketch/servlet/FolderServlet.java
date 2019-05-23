package com.codecool.sketch.servlet;

import com.codecool.sketch.dao.FolderDao;
import com.codecool.sketch.dao.database.DatabaseFolderDao;
import com.codecool.sketch.model.Folder;
import com.codecool.sketch.service.FolderService;
import com.codecool.sketch.service.exception.ServiceException;
import com.codecool.sketch.service.impl.ImplFolderServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/protected/folder")
public class FolderServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // List all folders of a user
        try (Connection connection = getConnection(getServletContext())) {
            FolderDao folderDao = new DatabaseFolderDao(connection);
            FolderService folderService = new ImplFolderServiceImpl(fetchUser(req), folderDao);
            List<Folder> folders = folderService.fetchAll();
            sendMessage(resp, SC_OK, folders);
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Create new folder
        try (Connection connection = getConnection(getServletContext())) {
            String name = req.getParameter("name");
            sendMessage(resp, SC_OK, "test");

            throw new ServiceException("Service not implemented yet");
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Rename folder
        try (Connection connection = getConnection(getServletContext())) {
            String name = req.getParameter("name");
            sendMessage(resp, SC_OK, "test");
            throw new ServiceException("Service not implemented yet");
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Delete folder
        try (Connection connection = getConnection(getServletContext())) {
            String id = req.getParameter("id");
            sendMessage(resp, SC_OK, "test");
            throw new ServiceException("Service not implemented yet");
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }
}

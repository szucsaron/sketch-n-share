package com.codecool.sketch.servlet;

import com.codecool.sketch.dao.FolderDao;
import com.codecool.sketch.dao.database.DatabaseFolderDao;
import com.codecool.sketch.model.Folder;
import com.codecool.sketch.service.FolderService;
import com.codecool.sketch.service.exception.ServiceException;
import com.codecool.sketch.service.impl.ImplFolderService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/protected/folder_share")
public class FolderShareServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // List all shared folders of a user
        try (Connection connection = getConnection(getServletContext())) {
            FolderDao folderDao = new DatabaseFolderDao(connection);
            FolderService folderService = new ImplFolderService(fetchUser(req), folderDao);
            folderService.validateAdminMode(fetchAdminMode(req));
            List<Folder> folders = folderService.fetchAllShared();
            sendMessage(resp, SC_OK, folders);
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }

}

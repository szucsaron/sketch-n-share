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

@WebServlet("/protected/folder_owner")
public class FolderOwnershipServlet extends AbstractServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Rename folder
        try (Connection connection = getConnection(getServletContext())) {
            String folderId = req.getParameter("folder_id");
            String ownerName = req.getParameter("owner_name");
            FolderDao folderDao = new DatabaseFolderDao(connection);
            FolderService folderService = new ImplFolderService(fetchUser(req), folderDao);
            folderService.validateAdminMode(fetchAdminMode(req));

            folderService.changeFolderOwner(folderId, ownerName);
            sendMessage(resp, SC_OK, "Folder owner changed");
        } catch (SQLException | ServiceException e) {
            handleError(resp, e);
        }
    }
}

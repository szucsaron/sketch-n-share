package com.codecool.sketch.servlet;


import com.codecool.sketch.dao.UserDao;
import com.codecool.sketch.dao.database.DatabaseUserDao;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.UserService;
import com.codecool.sketch.service.exception.ServiceException;
import com.codecool.sketch.service.impl.ImplUserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/folder_share_mgr")
public class FolderShareMgrServlet extends AbstractServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new ImplUserService(fetchUser(req), userDao);

            String folderId = req.getParameter("folder_id");
            List<User> sharedUsers = userService.fetchBySharedFolder(folderId);

            sendMessage(resp, HttpServletResponse.SC_OK, sharedUsers);


        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } catch (SQLException ex) {
            handleError(resp, ex);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new ImplUserService(fetchUser(req), userDao);

            String folderId = req.getParameter("folder_id");
            String userName = req.getParameter("user_name");
            userService.shareFolderWithUser(userName, folderId);

            sendMessage(resp, HttpServletResponse.SC_OK, "Folder shared");


        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } catch (SQLException ex) {
            handleError(resp, ex);
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            UserDao userDao = new DatabaseUserDao(connection);
            UserService userService = new ImplUserService(fetchUser(req), userDao);

            String folderId = req.getParameter("folder_id");
            String user_id = req.getParameter("user_id");
            userService.unshareFolderWithUser(user_id, folderId);

            sendMessage(resp, HttpServletResponse.SC_OK, "Folder unshared");


        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } catch (SQLException ex) {
            handleError(resp, ex);
        }
    }


}

package com.codecool.sketch.service.impl;

import com.codecool.sketch.dao.FolderDao;
import com.codecool.sketch.model.Folder;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.FolderService;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class ImplFolderService extends AbstractService implements FolderService {
    private FolderDao folderDao;
    public ImplFolderService(User user, FolderDao folderDao) {
        super(user);
        this.folderDao = folderDao;
    }

    public List<Folder> fetchAll() throws ServiceException, SQLException {
        if (adminMode) {
            return null;
        } else {
            return folderDao.fetchByUserId(fetchUserId());
        }
    }
}

package com.codecool.sketch.service.impl;

import com.codecool.sketch.dao.FolderDao;
import com.codecool.sketch.model.Folder;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.FolderService;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class ImplFolderServiceImpl extends ImplAbstractService implements FolderService {
    private FolderDao folderDao;

    public ImplFolderServiceImpl(User user, FolderDao folderDao) {
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

    public void createNew(String name) throws ServiceException, SQLException {
        folderDao.createNew(name, fetchUserId());
    }

    public void rename(String folderId, String name) throws ServiceException, SQLException {
        int folderIdVal = fetchInt(folderId, "folderId");
        if (adminMode) {
            folderDao.rename(folderIdVal, name);
        } else {
            folderDao.rename(fetchUserId(), folderIdVal, name);
        }
    }

    public void delete(String folderId) throws ServiceException, SQLException {
        int folderIdVal = fetchInt(folderId, "folderId");
        if (adminMode) {
            folderDao.delete(folderIdVal);
        } else {
            folderDao.delete(fetchUserId(), folderIdVal);
        }
    }


}

package com.codecool.sketch.service.impl;

import com.codecool.sketch.dao.FolderDao;
import com.codecool.sketch.model.Folder;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.FolderService;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class ImplFolderService extends ImplAbstractService implements FolderService {
    private FolderDao folderDao;

    public ImplFolderService(User user, FolderDao folderDao) {
        super(user);
        this.folderDao = folderDao;
    }

    public List<Folder> fetchAll() throws ServiceException, SQLException {
        if (adminMode) {
            return folderDao.fetchAll();
        } else {
            return folderDao.fetchByUserId(fetchUserId());
        }
    }

    public List<Folder> fetchAllShared() throws ServiceException, SQLException {
        return folderDao.fetchAllShared(fetchUserId());
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

    public void changeFolderOwner(String folderId, String ownerName) throws ServiceException, SQLException {
        if (adminMode) {
            folderDao.changeOwner(fetchInt(folderId, "folderId"), ownerName);
        } else {
            throw new ServiceException("User has no admin access to change ownership");
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

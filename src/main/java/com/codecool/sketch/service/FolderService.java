package com.codecool.sketch.service;

import com.codecool.sketch.model.Folder;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface FolderService extends AbstractService {
    List<Folder> fetchAll() throws ServiceException, SQLException;

    void createNew(String name) throws ServiceException, SQLException;

    void rename(String folderId, String name) throws ServiceException, SQLException;

    void delete(String folderId) throws ServiceException, SQLException;
}

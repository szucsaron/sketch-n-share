package com.codecool.sketch.service;

import com.codecool.sketch.model.Folder;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface FolderService {
    List<Folder> fetchAll() throws ServiceException, SQLException;
}

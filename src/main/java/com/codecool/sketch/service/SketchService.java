package com.codecool.sketch.service;

import com.codecool.sketch.model.Sketch;
import com.codecool.sketch.model.EmptySketchData;
import com.codecool.sketch.service.exception.ServiceException;


import java.sql.SQLException;
import java.util.List;

public interface SketchService  extends AbstractService {
    // Sketch fetchById(String id);

    List<EmptySketchData> fetchEmptiesByFolderId(String id) throws ServiceException, SQLException;

    List<EmptySketchData> fetchSharedEmptiesByFolderId(String id) throws ServiceException, SQLException;



    Sketch fetchSketchById(String id) throws ServiceException, SQLException;

    void create(String id, String name) throws ServiceException, SQLException;

    void update(String id, String folderId, String name, String content) throws ServiceException, SQLException;

    void rename(String id, String name) throws ServiceException, SQLException;


}

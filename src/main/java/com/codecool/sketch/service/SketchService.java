package com.codecool.sketch.service;

import com.codecool.sketch.model.Sketch;
import com.codecool.sketch.model.EmptySketchData;
import com.codecool.sketch.service.exception.ServiceException;


import java.sql.SQLException;
import java.util.List;

public interface SketchService  extends AbstracttService{
    // Sketch fetchById(String id);

    List<EmptySketchData> fetchEmptiesByFolderId(String id) throws ServiceException, SQLException;

    Sketch fetchSketchById(String id) throws ServiceException, SQLException;

    void update(String id, String folderId, String name, String content) throws ServiceException, SQLException;


}

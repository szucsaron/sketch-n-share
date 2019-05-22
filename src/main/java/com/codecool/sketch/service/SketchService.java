package com.codecool.sketch.service;

import com.codecool.sketch.model.Sketch;
import com.codecool.sketch.model.EmptySketchData;
import com.codecool.sketch.service.exception.ServiceException;


import java.sql.SQLException;
import java.util.List;

public interface SketchService {
    // Sketch fetchById(String id);

    List<EmptySketchData> fetchEmptiesByFolderId(String id) throws ServiceException, SQLException;

}

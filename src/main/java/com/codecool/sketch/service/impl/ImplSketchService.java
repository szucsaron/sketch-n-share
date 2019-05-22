package com.codecool.sketch.service.impl;

import com.codecool.sketch.dao.SketchDao;
import com.codecool.sketch.model.EmptySketchData;
import com.codecool.sketch.model.Sketch;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.SketchService;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class ImplSketchService extends AbstractService implements SketchService {
    private SketchDao sketchDao;

    public ImplSketchService(User user, SketchDao sketchDao) {
        super(user);
        this.sketchDao = sketchDao;
    }

    public List<EmptySketchData> fetchEmptiesByFolderId(String folderId) throws ServiceException, SQLException {
        if (adminMode) {
          return null;
        } else {
            return sketchDao.findByFolderId(fetchUserId(), fetchInt(folderId, "folderId"));
        }
    }

    public Sketch fetchSketchById(String id) throws ServiceException, SQLException {
        if (adminMode) {
            return null;
        } else {
            return sketchDao.findById(fetchUserId(), fetchInt(id, "folderId"));
        }
    }
}

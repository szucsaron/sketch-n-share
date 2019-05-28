package com.codecool.sketch.service.impl;

import com.codecool.sketch.dao.SketchDao;
import com.codecool.sketch.model.EmptySketchData;
import com.codecool.sketch.model.Sketch;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.SketchService;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class ImplSketchServiceImpl extends ImplAbstractService implements SketchService {
    private SketchDao sketchDao;

    public ImplSketchServiceImpl(User user, SketchDao sketchDao) {
        super(user);
        this.sketchDao = sketchDao;
    }

    public List<EmptySketchData> fetchEmptiesByFolderId(String folderId) throws ServiceException, SQLException {
        int folderIdVal = fetchInt(folderId, "folderId");
        if (adminMode) {
          return sketchDao.findByFolderId(folderIdVal);
        } else {
            return sketchDao.findByFolderId(fetchUserId(), folderIdVal);
        }
    }

    public Sketch fetchSketchById(String id) throws ServiceException, SQLException {
        int idVal = fetchInt(id, "id");

        if (adminMode) {
            return sketchDao.findById(idVal);
        } else {
            return sketchDao.findById(fetchUserId(), idVal);
        }
    }

    public void update(String id, String folderId, String name, String content) throws ServiceException, SQLException {
        int idVal = fetchInt(id, "id");
        int folderIdVal = fetchInt(folderId, "folderId");

        if (adminMode) {
            sketchDao.update(idVal, folderIdVal, name, content);
        } else {
            int userIdVal = fetchUserId();
            sketchDao.update(userIdVal, idVal, folderIdVal, name, content);
        }
    }

}

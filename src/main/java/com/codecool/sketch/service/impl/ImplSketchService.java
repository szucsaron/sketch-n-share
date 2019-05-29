package com.codecool.sketch.service.impl;

import com.codecool.sketch.dao.SketchDao;
import com.codecool.sketch.model.EmptySketchData;
import com.codecool.sketch.model.Sketch;
import com.codecool.sketch.model.User;
import com.codecool.sketch.service.SketchService;
import com.codecool.sketch.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class ImplSketchService extends ImplAbstractService implements SketchService {
    private SketchDao sketchDao;

    public ImplSketchService(User user, SketchDao sketchDao) {
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

    public List<EmptySketchData> fetchSharedEmptiesByFolderId(String id) throws ServiceException, SQLException {
        return sketchDao.findBySharedFolderId(fetchUserId(), fetchInt(id, "id"));
    }

    public Sketch fetchById(String id) throws ServiceException, SQLException {
        int idVal = fetchInt(id, "id");

        if (adminMode) {
            return sketchDao.findById(idVal);
        } else {
            return sketchDao.findById(fetchUserId(), idVal);
        }
    }

    public Sketch fetchSharedById(String id) throws ServiceException, SQLException{
        return sketchDao.findSharedById(fetchUserId(), fetchInt(id, "id"));
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

    public void create(String folderId, String name) throws ServiceException, SQLException {
        int folderIdVal = fetchInt(folderId, "folderId");
        if (adminMode) {
            sketchDao.create(folderIdVal, name);
        } else {
            sketchDao.create(fetchUserId(), folderIdVal, name);
        }
    }

    public void rename(String sketchId, String name) throws ServiceException, SQLException {
        int idVal = fetchInt(sketchId, "sketchId");
        if (adminMode) {
            sketchDao.rename(idVal, name);
        } else {
            sketchDao.rename(fetchUserId(), idVal, name);
        }
    }

}

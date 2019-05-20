package com.codecool.sketch.service;

import com.codecool.sketch.model.Folder;

import java.util.List;

public interface FolderService {
    List<Folder> fetchByUserId(int userId);

    void shareWithUser(String id, int userId);

    void unshareWithUser(String id, int userId);

}

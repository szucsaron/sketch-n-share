package com.codecool.sketch.dto;

import com.codecool.sketch.model.Folder;
import com.codecool.sketch.model.OwnedItem;

public class FolderOwnerDto implements OwnedItem {
    private Folder folder;
    private String userName;

    public FolderOwnerDto(Folder folder, String userName) {
        this.folder = folder;
        this.userName = userName;
    }

    public int getId() {
        return folder.getId();
    }

    public String getName() {
        return folder.getName();
    }

    public String getOwner() {
        return userName;
    }

}

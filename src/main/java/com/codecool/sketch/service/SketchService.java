package com.codecool.sketch.service;

import com.codecool.sketch.model.SketchDto;
import com.codecool.sketch.model.Sketch;


import java.util.List;

public interface SketchService {
    SketchDto fetchDtoById(String id);

    List<Sketch> fetchEmptiesByFolderId(String id);

    void modify(String id, String name, String body);

    void create(String id, String name, String body);

}

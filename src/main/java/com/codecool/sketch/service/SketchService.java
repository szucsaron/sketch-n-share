package com.codecool.sketch.service;

import com.codecool.sketch.model.EmptySketch;
import com.codecool.sketch.model.Sketch;

import java.util.List;

public interface SketchService {
    Sketch fetchById(String id);

    List<EmptySketch> fetchEmptiesByFolderId(String id);

    void modify(String id, String name, String body);

    void create(String id, String name, String body);

}

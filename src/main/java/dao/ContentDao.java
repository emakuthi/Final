package dao;

import models.Content;


import java.util.List;

public interface ContentDao {
    // LIST
    List<Content> getAll();
    List<Content> getAllCoarseContentByCoarse(int coarseid);

    // CREATE
    void add(Content content);

    // READ
    Content findById(int id);

    // UPDATE
    void update(String url,int subCourseid);

    // DELETE
    void deleteById(int id);

    void clearAllsubCourseContent();
}

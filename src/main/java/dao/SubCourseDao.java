package dao;

import models.Content;
import models.SubCourse;

import java.util.List;

public interface SubCourseDao {

    // LIST
    List<SubCourse> getAll();
    List<Content> getAllSubCoarsesByCoarse(int coarseid);

    // CREATE
    void add(SubCourse subCourse);

    // READ
    SubCourse findById(int id);

    // UPDATE
    void update(String name, String url, int courseid);

    // DELETE
    void deleteById(int id);

    void clearAllsubCourses();
}

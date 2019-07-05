package dao;

import models.Content;
import models.SubCourse;

import java.util.List;

public interface SubCourseDao {

    // LIST
    List<SubCourse> getAll();
    List<Content> getAllSubCoarsesByCoarse(String coarse_name);

    // CREATE
    void add(SubCourse subCourse);

    // READ
    SubCourse findById(int id);

    // UPDATE
    void update(String coarse_name, String name, String url);

    // DELETE
    void deleteById(int id);

    void clearAllsubCourses();
}
